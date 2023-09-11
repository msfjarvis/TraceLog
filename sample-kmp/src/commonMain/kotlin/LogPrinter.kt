import com.github.ajalt.mordant.rendering.TextColors.*
import com.github.ajalt.mordant.terminal.Terminal

object LogPrinter {
  private val messages = arrayListOf<String>()

  private const val MATCH_ARROW = "arrow"
  private const val MATCH_IDENTIFIER = "identifier"
  private const val MATCH_PARAMS = "params"
  private const val MATCH_TIME = "time"
  private const val MATCH_RETURN = "retval"

  private val ENTER_REGEX =
    "(?<$MATCH_ARROW>⇢) (?<$MATCH_IDENTIFIER>[a-zA-Z].*)\\((?<$MATCH_PARAMS>.*)\\)".toRegex()
  private val EXIT_REGEX =
    "(?<$MATCH_ARROW>⇠) (?<$MATCH_IDENTIFIER>[a-zA-Z].*) \\[(?<$MATCH_TIME>.*)] = (?<$MATCH_RETURN>.*)"
      .toRegex()

  private fun MatchResult.getMatch(groupName: String): String {
    return groups[groupName]!!.value
  }

  fun submitLog(message: String) {
    messages += message
  }

  fun printLogs() {
    val t = Terminal()
    messages.forEach { msg ->
      var matches = ENTER_REGEX.find(msg)
      if (matches != null) {
        val params =
          matches.getMatch(MATCH_PARAMS).split(", ").joinToString(", ") {
            val split = it.split("=")
            if (split.size == 1) {
              ""
            } else {
              val (name, value) = (split[0] to split[1])
              "${brightYellow(name)}=${brightBlue(value)}"
            }
          }
        t.println(
          """
        ${red(matches.getMatch(MATCH_ARROW))} ${green(matches.getMatch(MATCH_IDENTIFIER))}($params)
      """
            .trimIndent()
        )
      } else {
        matches = EXIT_REGEX.matchEntire(msg)
        if (matches != null) {
          t.println(
            """
          ${red(matches.getMatch(MATCH_ARROW))} ${green(matches.getMatch(MATCH_IDENTIFIER))} [${blue(matches.getMatch(
              MATCH_TIME))}] = ${brightWhite(matches.getMatch(MATCH_RETURN))}
        """
              .trimIndent()
          )
        }
      }
    }
  }
}
