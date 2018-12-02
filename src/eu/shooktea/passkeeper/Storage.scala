package eu.shooktea.passkeeper

import javafx.beans.property.SimpleListProperty
import javafx.collections.{FXCollections, ObservableList}

object Storage {

  def savedCipherableElement(element: Cipherable[_]) : Unit = {
    if (currentlyModifiedIndex == -1)
      store(element)
    else
      update(currentlyModifiedIndex, element)
    currentlyModifiedIndex = -1
  }

  def store(element: Cipherable[_]) : Unit = {
    elements add element
  }

  def update(index: Int, element: Cipherable[_]) : Unit = {
    elements set(index, element)
  }

  def remove(index: Int) : Unit = {
    elements remove index
  }

  def getElements() : ObservableList[Cipherable[_]] = elements.get()
  def setElements(list: ObservableList[Cipherable[_]]) : Unit = elements set list
  def elementsProperty() : SimpleListProperty[Cipherable[_]] = elements

  def ++(element: Cipherable[_]) : Unit = store(element)
  def --(index: Int) : Unit = remove(index)

  private val elements: SimpleListProperty[Cipherable[_]] = new SimpleListProperty(FXCollections.observableArrayList[Cipherable[_]]())
  private var currentlyModifiedIndex: Int = -1
}
