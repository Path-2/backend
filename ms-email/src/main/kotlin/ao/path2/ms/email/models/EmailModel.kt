package ao.path2.ms.email.models

class EmailModel {
  var id: Long = 0
  lateinit var subject: String
  lateinit var name: String
  lateinit var token: String
  lateinit var to: List<String>
  lateinit var template: String
}