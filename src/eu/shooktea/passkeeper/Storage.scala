package eu.shooktea.passkeeper

object Storage {

  def savedCipherableElement(element: Cipherable[_]) : Unit = {
    store(element)
  }

  def store(element: Cipherable[_]) : Unit = {
    elements = element :: elements
  }

  def remove(index: Int) : Unit = {
    elements = elements.take(index) ++ elements.drop(index + 1)
  }

  def allElements() : List[Cipherable[_]] = elements

  def ++(element: Cipherable[_]) : Unit = store(element)
  def --(index: Int) : Unit = remove(index)

  private var elements: List[Cipherable[_]] = List()
}
