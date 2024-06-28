package sky.pro.Kurs2.Service;

import sky.pro.Kurs2.Model.Question;

import java.util.Collection;

public interface ExaminerService {

    Collection<Question> getQuestions(int amount);

}
