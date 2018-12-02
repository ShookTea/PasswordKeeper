package eu.shooktea.passkeeper

object Storage {

  def savedCipherableElement(element: Cipherable[_]) : Unit = {
    if (currentlyModifiedIndex == -1)
      store(element)
    else
      update(currentlyModifiedIndex, element)
    currentlyModifiedIndex = -1
  }

  def store(element: Cipherable[_]) : Unit = {
    elements = elements :+ element
  }

  def update(index: Int, element: Cipherable[_]) : Unit = {
    elements.update(index, element)
  }

  def remove(index: Int) : Unit = {
    elements = elements.take(index) ++ elements.drop(index + 1)
  }

  def allElements() : Array[Cipherable[_]] = elements

  def ++(element: Cipherable[_]) : Unit = store(element)
  def --(index: Int) : Unit = remove(index)

  private var elements: Array[Cipherable[_]] = Array()
  private var currentlyModifiedIndex: Int = -1
}
