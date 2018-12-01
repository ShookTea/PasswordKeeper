package eu.shooktea.passkeeper

import org.junit.{Test, Assert}

class PasswordGeneratorTest {

  @Test
  def generateRandomPassword() : Unit = {
    val passwordLength: Int = 15
    val password: String = PasswordGenerator(passwordLength)
    Assert.assertEquals(passwordLength, password.length)
  }
}
