package ao.path2.app.core.domain

data class Role(val name: String) {
  var privileges: List<Privilege> = listOf()
}