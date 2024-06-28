package sky.pro.Kurs2;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sky.pro.Kurs2.Exception.QuestionAlreadyAddedException;
import sky.pro.Kurs2.Exception.QuestionNotFoundException;
import sky.pro.Kurs2.Exception.QuestionsAreEmptyException;
import sky.pro.Kurs2.Model.Question;
import sky.pro.Kurs2.Service.JavaQuestionService;
import sky.pro.Kurs2.Service.QuestionService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class JavaQuestionServiceTest {

    private final QuestionService questionService = new JavaQuestionService();

    private final List<Question> questions = List.of(
            new Question("Вопрос 1", "Ответ 1"),
            new Question("Вопрос 2", "Ответ 2"),
            new Question("Вопрос 3", "Ответ 3")
    );

    @BeforeEach
    public void beforeEach() {
        questions.forEach(question -> questionService.add(question));
    }

    @AfterEach
    public void afterEach() {
        new ArrayList<>(questionService.getAll()).forEach(question -> questionService.remove(question));;
    }

    @Test
    public void add1Test() {

        int was = questionService.getAll().size();
        Question expected = new Question("Вопрос 4", "Вопрос 4");
        assertThat(questionService.getAll()).doesNotContain(expected);
        Question actual = questionService.add(new Question("Вопрос 4", "Вопрос 4"));

        assertThat(actual).isEqualTo(expected);
        assertThat(questionService.getAll()).hasSize(was +1);
        assertThat(questionService.getAll().contains(expected));

    }

    @Test
    public void add2Test() {

        int was = questionService.getAll().size();
        Question expected = new Question("Вопрос 4", "Вопрос 4");
        assertThat(questionService.getAll()).doesNotContain(expected);
        Question actual = questionService.add("Вопрос 4", "Вопрос 4");

        assertThat(actual).isEqualTo(expected);
        assertThat(questionService.getAll()).hasSize(was + 1);
        assertThat(questionService.getAll().contains(expected));

    }

    @Test
    public void add1NegativeTest() {

        assertThatExceptionOfType(QuestionAlreadyAddedException.class).isThrownBy(()-> questionService.add(new Question("Вопрос 1", "Ответ 1")));
    }

    @Test
    public void add2NegativeTest() {

        assertThatExceptionOfType(QuestionAlreadyAddedException.class).isThrownBy(()-> questionService.add("Вопрос 1", "Ответ 1"));
    }

    @Test
    public void removeTest() {

        int was = questionService.getAll().size();
        Question expected = new Question("Вопрос 1", "Ответ 1");
        assertThat(questionService.getAll()).contains(expected);
        Question actual = questionService.remove(new Question("Вопрос 1", "Ответ 1"));

        assertThat(actual).isEqualTo(expected);
        assertThat(questionService.getAll()).hasSize(was - 1);
        assertThat(questionService.getAll()).doesNotContain(expected);

    }

    @Test
    public void removeNegativeTest() {

        assertThatExceptionOfType(QuestionNotFoundException.class).isThrownBy(()-> questionService.remove(new Question("Вопрос 4", "Ответ 4")));
    }

    @Test
    public void getAllTest() {

        assertThat(questionService.getAll()).containsExactlyInAnyOrderElementsOf(questions);

    }

    @Test
    public void getRandomQuestionTest() {

        assertThat(questionService.getRandomQuestion()).isIn(questionService.getAll());

    }

    @Test
    public void getRandomQuestionNegativeTest() {

        afterEach();;

        assertThatExceptionOfType(QuestionsAreEmptyException.class).isThrownBy(() -> questionService.getRandomQuestion());

    }

}
