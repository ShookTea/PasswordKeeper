package eu.shooktea.passkeeper

import scala.util.Random

object PasswordGenerator {
  def apply(passwordLength: Int): String = {
    val random = new Random()
    val characters = smallLetters ++ largeLetters ++ digits ++ specialCharacters
    val password = new StringBuilder()
    for (_ <- 0 until passwordLength) {
      password append characters(random nextInt characters.length)
    }
    password.toString()
  }

  def charactersFromRange(start: Char, end: Char) : List[Char] =
    Range.apply(start.toInt, end.toInt + 1).toList.map(int => int.asInstanceOf[Char])

  def smallLetters : List[Char] = charactersFromRange('a', 'z')
  def largeLetters : List[Char] = charactersFromRange('A', 'Z')
  def digits : List[Char] = charactersFromRange('0', '9')
  def specialCharacters: List[Char] = charactersFromRange('!', '/') ++ charactersFromRange(':', '@')
}
