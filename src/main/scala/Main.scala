package org.stingray.contester.pwprinter

import java.io.File
import com.typesafe.config.ConfigFactory
import org.apache.commons.io.{Charsets, FileUtils}
import slick.jdbc.GetResult

import java.nio.charset.StandardCharsets
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}

case class Team(schoolName: String, teamNum: Int, teamName: String, username: String,
                     password: String) {
  def schoolNameWithNum: String = s"$schoolName #$teamNum"
  def teamFullName: String = {
    schoolNameWithNum +
      (if (!teamName.isEmpty) s": $teamName"
      else "")
  }
}
case class Contest(id: Int, name: String)

case class ContestTeam(contest: Contest, team: Team)

object Main extends App {
  import org.stingray.contester.dbmodel.MyPostgresProfile.api._
  import org.stingray.contester.dbmodel.SlickModel
  import scala.concurrent.duration._

  private[this] val db = Database.forConfig("default")

  val allTeamsQuery = Compiled(for {
    assg <- SlickModel.assignments
    cont <- SlickModel.contests if assg.contest === cont.id
    team <- SlickModel.teams if assg.team === team.id
    scho <- SlickModel.schools if team.school === scho.id
  } yield (cont.id, cont.name, scho.name, team.num, team.name, assg.username, assg.password))

  val teams = Await.result(db.run(allTeamsQuery.result).map { all =>
    all.map { x =>
      ContestTeam(
        Contest(x._1, x._2),
        Team(x._3, x._4, x._5, x._6, x._7)
      )
    }
  }, 10.seconds).groupBy(_.contest.id)

  for (c <- teams) {
    FileUtils.writeStringToFile(new File(s"stubs${c._1}.tex"), tex.passwords(c._2.sortBy(_.team.teamFullName)).body, StandardCharsets.UTF_8)
  }
}