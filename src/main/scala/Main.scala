package org.stingray.contester.pwprinter

import com.typesafe.config.ConfigFactory
import slick.jdbc.GetResult

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}

trait Team {
  def schoolName: String
  def teamNum: Option[Int]
  def teamName: String

  def schoolNameWithNum: String = s"$schoolName" + teamNum.map(x => s" #$x").getOrElse("")
  def teamFullName: String = {
    schoolNameWithNum +
      (if (!teamName.isEmpty) s": $teamName"
      else "")
  }
}

case class LocalTeam(localId: Int, schoolName: String, teamNum: Option[Int], teamName: String) extends Team
case class Contest(id: Int, name: String)

case class ContestTeam(contest: Contest, team: Team)

object Main extends App {

  import slick.driver.MySQLDriver.api._
  import scala.concurrent.duration._

  private val db = Database.forConfig("default")

  implicit val toContestTeam = GetResult(r =>
    ContestTeam(
      Contest(r.nextInt(), r.nextString()),
    LocalTeam(r.nextInt(), r.nextString(), r.nextIntOption(), r.nextString())
    )
  )

  val teams = Await.result(db.run(
    sql"""select
          Assignments.Contest as ContestID,
          Contests.Name as ContestName,
          Assignments.LocalID as LocalID,
          Schools.Name as SchoolName,
          Teams.Num as TeamNum,
          Teams.Name as TeamName,
          from Assignments, Participants, Contests, Teams, Schools
         where
         Assignments.Contest = Contests.ID and Participants.Contest = Contests.ID and
         Participants.LocalID = Assignments.LocalID and Participants.Team = Teams.ID and
         Teams.School = Schools.ID
    """.as[ContestTeam]
  ), 10.seconds)

  println(teams)

}