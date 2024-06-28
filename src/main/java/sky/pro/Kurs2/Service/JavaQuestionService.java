package sky.pro.Kurs2.Service;

import org.springframework.stereotype.Service;
import sky.pro.Kurs2.Exception.QuestionAlreadyAddedException;
import sky.pro.Kurs2.Exception.QuestionNotFoundException;
import sky.pro.Kurs2.Exception.QuestionsAreEmptyException;
import sky.pro.Kurs2.Model.Question;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class JavaQuestionService implements QuestionService{

    private final Set<Question> questions = new HashSet<>();

    @Override
    public Question add(String question, String answer) {
        return add(new Question(question, answer));
    }

    @Override
    public Question add(Question question) {
        if (!questions.add(question)) {
            throw new QuestionAlreadyAddedException();
        }
        return question;
    }

    @Override
    public Question remove(Question question) {
        if (!questions.contains(question)) {
            throw new QuestionNotFoundException();
        }
        questions.remove(question);
        return question;
    }

    @Override
    public Collection<Question> getAll() {
        return Collections.unmodifiableCollection(questions);
    }

    @Override
    public Question getRandomQuestion() {
        if (questions.isEmpty()) {
            throw new QuestionsAreEmptyException();
        }
        int randomIndex = ThreadLocalRandom.current().nextInt(questions.size());
        return new ArrayList<>(questions).get(randomIndex);
    }
}
