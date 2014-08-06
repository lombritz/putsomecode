package models

abstract class Msg {
  def getDetail: String
  def isError: Boolean
  def isInfo: Boolean
}
case class ErrorMsg(detail: String) extends Msg {
  def getDetail = detail
  def isError = true
  def isInfo = false
}
case class InfoMsg(detail: String) extends Msg {
  def getDetail = detail
  def isError = false
  def isInfo = true
}