package ao.path2.ms.user.models

data class EmailModel(
  val subject: String,
  val name: String,
  val token: String?,
  val to: List<String>,
  val template: String
)
/*{
  var id: Long = 0
  var data: Map<String, Any> = mapOf()
  var subject: String = ""
  var to: Array<String> = arrayOf()
  var template: String = ""
}*/