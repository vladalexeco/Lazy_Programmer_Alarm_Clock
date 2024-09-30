package ru.vladalexeco.lazyprogrammer.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.vladalexeco.lazyprogrammer.R
import ru.vladalexeco.lazyprogrammer.domain.AlarmTask
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.AccentColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.BackgroundColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.CardColor
import ru.vladalexeco.lazyprogrammer.presentation.ui.theme.MainTextColor

@Composable
fun AlarmTaskScreen() {
    // TODO это мок объект. В дальнейшем надо заменить его на объект, который будет приходить
    // TODO из базы данных (удаленной или локальной) при помощи вьюмодели
    val alarmTask = AlarmTask(
        id = 0,
        quest = "Дано начальное значение переменной m. Какое значение m выведет" +
        " функция println()?",
        code = "val str = \"String\"\nvar m = 0 \nvar count = 3 \n" +
        "while (count > 0) {\n    m++ \n    count-- \n} \n" +
        "println(m)",
        choiceOptions = listOf("1", "2", "3" ,"4"),
        rightAnswer = 2,
        language = "kotlin",
        complexity = 1
    )

    val codeText = alarmTask.code

    val annotatedCode = buildAnnotatedString {
        val languageKeywords = listOf("val", "var", "fun", "class", "object", "interface", "if", "else", "for",
            "while", "when", "do", "return", "break", "continue", "true", "false",
            "null", "this", "super", "in", "is", "as", "try", "catch", "finally",
            "throw", "import", "package", "out", "by", "constructor", "delegate",
            "dynamic", "field", "file", "get", "set", "init", "lateinit", "data",
            "sealed", "inner", "override", "abstract", "private", "protected",
            "public", "open", "final", "vararg", "suspend", "inline", "noinline",
            "crossinline", "reified", "typealias", "companion", "const", "operator",
            "infix", "external", "tailrec", "enum", "annotation")

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
                keywordRegex.matches(matchResult.value) -> SpanStyle(color = Color(0xFFFF9800)) // Оранжевый для ключевых слов
                stringLiteralRegex.matches(matchResult.value) -> SpanStyle(color = Color(0xFF4CAF50)) // Зеленый для строковых литералов
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, end = 12.dp)
        ) {
            Text(
                text = "Задание",
                style = TextStyle(color = MainTextColor, fontSize = 24.sp)
            )
            Image(
                modifier = Modifier.align(Alignment.CenterEnd),
                painter = painterResource(id = R.drawable.menu_dots_vertical),
                colorFilter = ColorFilter.tint(MainTextColor),
                contentDescription = null
            )
        }

        Text(
            modifier = Modifier.padding(
                start = 16.dp,
                top = 16.dp
            ),
            text = "Сложность: ${alarmTask.complexity}",
            style = TextStyle(color = AccentColor, fontSize = 20.sp)
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 16.dp),
            text = alarmTask.quest,
            style = TextStyle(color = MainTextColor, fontSize = 20.sp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 16.dp)
                .background(color = CardColor, shape = RoundedCornerShape(12.dp))
                .border(
                    width = 1.dp,
                    color = AccentColor,
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            BasicText(
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp),
                text = annotatedCode,
                style = TextStyle(
                    color = MainTextColor,
                    fontSize = 20.sp,
                    lineHeight = 32.sp,
                    fontFamily = FontFamily.Monospace
                )
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun AlarmTaskScreenPreview() {
    AlarmTaskScreen()
}