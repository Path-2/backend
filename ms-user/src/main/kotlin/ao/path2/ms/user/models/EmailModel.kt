package ao.path2.ms.user.models

class EmailModel {
  var id: Long = 0
  var data: Map<String, Any> = mapOf()
  var subject: String = ""
  var to: Array<String> = arrayOf()
  var template: String = ""
}