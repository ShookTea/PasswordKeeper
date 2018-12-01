package eu.shooktea.passkeeper

trait Cipherable[T <: Cipherable[T]] {
  def toBytes(): Array[Byte]
  def fromBytes(bytes:Array[Byte]) : T
}
