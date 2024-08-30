import java.util.*;
import java.util.concurrent.*;

public class QuizApp {
    static class QuizQuestion {
        String question;
        String[] options;
        int correctAnswerIndex;

        public QuizQuestion(String question, String[] options, int correctAnswerIndex) {
            this.question = question;
            this.options = options;
            this.correctAnswerIndex = correctAnswerIndex;
        }
    }

    public static void main(String[] args) {
        List<QuizQuestion> quizQuestions = loadQuestions();
        int totalQuestions = quizQuestions.size();
        int score = 0;
        Scanner scanner = new Scanner(System.in);

        for (QuizQuestion question : quizQuestions) {
            displayQuestion(question);
            // Start the timer
            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            Future<Integer> future = scheduler.submit(() -> {
                try {
                    return scanner.nextInt();
                } catch (InputMismatchException e) {
                    scanner.next(); // Clear invalid input
                    return -1; // Invalid answer
                }
            });

            int userAnswer = -1;
            try {
                userAnswer = future.get(10, TimeUnit.SECONDS); // 10-second timer
            } catch (TimeoutException e) {
                System.out.println("Time's up!");
                future.cancel(true);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                scheduler.shutdownNow();
            }

            boolean isCorrect = (userAnswer == question.correctAnswerIndex );
            score += isCorrect ? 1 : 0;

            System.out.println(isCorrect ? "Correct!" : "Incorrect. The correct answer was " + (question.correctAnswerIndex + 1));
        }

        displayResults(score, totalQuestions);
    }

    static List<QuizQuestion> loadQuestions() {
        List<QuizQuestion> questions = new ArrayList<>();
        questions.add(new QuizQuestion("What is the largest state in the United States by land area?", new String[]{"Texas", "California", "Montana", "Alaska"}, 4));
        questions.add(new QuizQuestion("What is the famous painting by Leonardo da Vinci that depicts a woman with mysterious smile?", new String[]{"The last Supper", "The Scream", "The Mona Lisa", "Starry Night"},3));
        questions.add(new QuizQuestion("Who was the author of the Mahabharata, according to Hindu tradition?", new String[]{"Valmiki", "Vyasa", "Kalidasa", "Bharavi"}, 2));
        return questions;
    }

    static void displayQuestion(QuizQuestion question) {
        System.out.println(question.question);
        for (int i = 0; i < question.options.length; i++) {
            System.out.println((i + 1) + ": " + question.options[i]);
        }
    }

    static void displayResults(int score, int totalQuestions) {
        System.out.println("Your final score is: " + score + " out of " + totalQuestions);
    }
}
