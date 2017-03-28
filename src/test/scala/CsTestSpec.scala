import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by wong on 28/03/17.
  */
class CsTestSpec extends FlatSpec with Matchers {

  val words = CsTest.words

  val firstAuto = List(
    "Project free tv Priceline",
    "Progressive",
    "Project runway",
    "Proactive",
    "Programming",
    "Progeria",
    "Progesterone",
    "Progenex",
    "Procurable",
    "Processor",
    "Proud"
  )

  val auto = List(
    "Progenex",
    "Progeria",
    "Progesterone",
    "Programming"
  )

  val p = List("Pandora", "Paypal", "Pg&e", "Phone")



  "A simple prefix" should "return the right list" in {
    CsTest.getWords("pro", words) should equal(firstAuto.sorted.take(4))
    CsTest.getWords("prog", words) should equal(auto)
  }

  "A single letter" should "return the right list" in {
    CsTest.getWords("p", words) should equal(p)
  }


}
