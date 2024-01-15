package me.astoriamine.quiz.text;

public class FormattedText extends TextEnvelope {

    public FormattedText(Text origin, Object... args) {
        super(() -> String.format(origin.asString(), args));
    }
}
