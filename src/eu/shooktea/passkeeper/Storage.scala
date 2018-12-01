package eu.shooktea.passkeeper

object Storage {

  def store(element: String) : Unit = {
    elements = element :: elements
  }

  def remove(index: Int) : Unit = {
    elements = elements.take(index) ++ elements.drop(index + 1)
  }

  def getAll() : List[String] = elements

  def ++(element: String) : Unit = store(element)
  def --(index: Int) : Unit = remove(index)

  private var elements: List[String] = List()
}
