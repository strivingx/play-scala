package models

// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.jdbc.MySQLProfile
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.jdbc.JdbcProfile

  import profile.api._
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = TUser.schema

  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table TUser
    *
    * @param id       Database column id SqlType(BIGINT UNSIGNED), AutoInc, PrimaryKey
    * @param username Database column username SqlType(VARCHAR), Length(20,true)
    * @param password Database column password SqlType(VARCHAR), Length(20,true)
    * @param mail     Database column mail SqlType(VARCHAR), Length(255,true), Default(None)
    * @param gender   Database column gender SqlType(BIT), Default(None)
    * @param age      Database column age SqlType(SMALLINT), Default(None) */
  case class TUserRow(id: Long, username: String, password: String, mail: Option[String] = None, gender: Option[Boolean] = None, age: Option[Int] = None)

  /** GetResult implicit for fetching TUserRow objects using plain SQL queries */
  implicit def GetResultTUserRow(implicit e0: GR[Long], e1: GR[String], e2: GR[Option[String]], e3: GR[Option[Boolean]], e4: GR[Option[Int]]): GR[TUserRow] = GR {
    prs =>
      import prs._
      TUserRow.tupled((<<[Long], <<[String], <<[String], <<?[String], <<?[Boolean], <<?[Int]))
  }

  /** Table description of table t_user. Objects of this class serve as prototypes for rows in queries. */
  class TUser(_tableTag: Tag) extends profile.api.Table[TUserRow](_tableTag, Some("test"), "t_user") {
    def * = (id, username, password, mail, gender, age) <> (TUserRow.tupled, TUserRow.unapply)

    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(username), Rep.Some(password), mail, gender, age).shaped.<>({ r => import r._; _1.map(_ => TUserRow.tupled((_1.get, _2.get, _3.get, _4, _5, _6))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(BIGINT UNSIGNED), AutoInc, PrimaryKey */
    val id: Rep[Long] = column[Long]("id", O.AutoInc, O.PrimaryKey)
    /** Database column username SqlType(VARCHAR), Length(20,true) */
    val username: Rep[String] = column[String]("username", O.Length(20, varying = true))
    /** Database column password SqlType(VARCHAR), Length(20,true) */
    val password: Rep[String] = column[String]("password", O.Length(20, varying = true))
    /** Database column mail SqlType(VARCHAR), Length(255,true), Default(None) */
    val mail: Rep[Option[String]] = column[Option[String]]("mail", O.Length(255, varying = true), O.Default(None))
    /** Database column gender SqlType(BIT), Default(None) */
    val gender: Rep[Option[Boolean]] = column[Option[Boolean]]("gender", O.Default(None))
    /** Database column age SqlType(SMALLINT), Default(None) */
    val age: Rep[Option[Int]] = column[Option[Int]]("age", O.Default(None))

    /** Uniqueness Index over (username) (database name un_username) */
    val index1 = index("un_username", username, unique = true)
  }

  /** Collection-like TableQuery object for table TUser */
  lazy val TUser = new TableQuery(tag => new TUser(tag))
}
