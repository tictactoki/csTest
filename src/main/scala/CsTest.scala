import scala.annotation.tailrec

/**
  * Created by wong on 28/03/17.
  */
object CsTest {

  /**
    * words list
    */
  val words = List(
    "Pandora",
    "Pinterest",
    "Paypal",
    "Pg&e",
    "Project free tv Priceline",
    "Press democrat",
    "Progressive",
    "Project runway",
    "Proactive",
    "Programming",
    "Progeria",
    "Progesterone",
    "Progenex",
    "Procurable",
    "Processor",
    "Proud",
    "Print",
    "Prank",
    "Bowl",
    "Owl",
    "River",
    "Phone",
    "Kayak",
    "Stamps",
    "Reprobe"
  )

  /**
    *
    * @param input
    * @param list
    * @return the list with input prefix
    */
  def getWords(input: String, list: List[String] = words) = {

    @tailrec
    def getWords(input: String, list: List[String], res: List[String] = Nil): List[String] = list match {
      case Nil => res
      case head::tail =>
        if(head.toLowerCase.startsWith(input)) getWords(input,tail,head :: res)
        else getWords(input, tail, res)
    }

    getWords(input.toLowerCase,list, Nil).reverse.sorted.take(4)
  }



}
