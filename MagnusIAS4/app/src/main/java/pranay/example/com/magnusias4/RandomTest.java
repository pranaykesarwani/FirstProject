package pranay.example.com.magnusias4;

public class RandomTest {
    String question_num,question,options,answers,description,question_type,pdf,video_url,hints;

    public RandomTest(String question_num, String question, String options, String answers, String description, String question_type, String pdf, String video_url, String hints) {
        this.question_num = question_num;
        this.question = question;
        this.options = options;
        this.answers = answers;
        this.description = description;
        this.question_type = question_type;
        this.pdf = pdf;
        this.video_url = video_url;
        this.hints = hints;
    }

    public String getQuestion_num() {
        return question_num;
    }

    public String getQuestion() {
        return question;
    }

    public String getOptions() {
        return options;
    }

    public String getAnswers() {
        return answers;
    }

    public String getDescription() {
        return description;
    }

    public String getQuestion_type() {
        return question_type;
    }

    public String getPdf() {
        return pdf;
    }

    public String getVideo_url() {
        return video_url;
    }

    public String getHints() {
        return hints;
    }
}
