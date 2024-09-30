package ru.vladalexeco.lazyprogrammer.core.util.util_functions

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

fun buildColoredString(language: String, codeText: String): AnnotatedString = buildAnnotatedString {

    val languageKeywords = when {
        language == "kotlin" -> listOf("val", "var", "fun", "class", "object", "interface",
            "if", "else", "for", "while", "when", "do", "return", "break", "continue", "true",
            "false", "null", "this", "super", "in", "is", "as", "try", "catch", "finally",
            "throw", "import", "package", "out", "by", "constructor", "delegate",
            "dynamic", "field", "file", "get", "set", "init", "lateinit", "data",
            "sealed", "inner", "override", "abstract", "private", "protected",
            "public", "open", "final", "vararg", "suspend", "inline", "noinline",
            "crossinline", "reified", "typealias", "companion", "const", "operator",
            "infix", "external", "tailrec", "enum", "annotation")

        else -> emptyList()
    }

    val keywordRegex = Regex("\\b(${languageKeywords.joinToString("|")})\\b")
    val stringLiteralRegex = Regex("(\"[^\"]*\"|'[^']*')")
    val numberRegex = Regex("-?\\d+(\\.\\d+)?")

    var lastIndex = 0

    val matches = mutableListOf<MatchResult>()
    matches.addAll(stringLiteralRegex.findAll(codeText))
    matches.addAll(keywordRegex.findAll(codeText))
    matches.addAll(numberRegex.findAll(codeText))

    val sortedMatches = matches.distinctBy { it.range }.sortedBy { it.range.first }

    sortedMatches.forEach { matchResult ->
        append(codeText.substring(lastIndex, matchResult.range.first))

        val style = when  {
            keywordRegex.matches(matchResult.value) -> SpanStyle(color = Color(0xFFFF9800))
            stringLiteralRegex.matches(matchResult.value) -> SpanStyle(color = Color(0xFF4CAF50))
            numberRegex.matches(matchResult.value) -> SpanStyle(color = Color(0xFF2196F3))
            else -> SpanStyle(color = Color(0xFFE6DEE3))
        }

        withStyle(style) {
            append(matchResult.value)
        }

        lastIndex = matchResult.range.last + 1
    }


    if (lastIndex < codeText.length) {
        append(codeText.substring(lastIndex))
    }
}