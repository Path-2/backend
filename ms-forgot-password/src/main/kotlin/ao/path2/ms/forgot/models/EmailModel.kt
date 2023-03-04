package ao.path2.ms.forgot.models

data class EmailModel(
  val subject: String,
  val name: String,
  val token: String,
  val to: List<String>,
  val template: String
)
