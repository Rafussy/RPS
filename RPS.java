import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class RPS extends JFrame implements ActionListener {

    private JLabel computerChoiceLabel;
    private JLabel resultLabel;
    private JButton rockButton;
    private JButton paperButton;
    private JButton scissorsButton;

    private CompetitiveLearningAlgorithm competitiveLearning;

    public RPS() {
        setTitle("Rock-Paper-Scissors Game");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1));

        computerChoiceLabel = new JLabel("Computer's Choice: ");
        resultLabel = new JLabel("Result: ");
        rockButton = new JButton("Rock");
        paperButton = new JButton("Paper");
        scissorsButton = new JButton("Scissors");

        rockButton.addActionListener(this);
        paperButton.addActionListener(this);
        scissorsButton.addActionListener(this);

        add(computerChoiceLabel);
        add(resultLabel);
        add(rockButton);
        add(paperButton);
        add(scissorsButton);

        // Initialize the Competitive Learning Algorithm with 3 neurons (Rock, Paper, Scissors)
        competitiveLearning = new CompetitiveLearningAlgorithm(3);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String userChoice = e.getActionCommand();
        String[] choices = {"Rock", "Paper", "Scissors"};
        String computerChoice = competitiveLearning.predict(); // Use the Competitive Learning Algorithm to predict

        // Train the Competitive Learning Algorithm with the user's choice
        competitiveLearning.train(userChoice);

        // Determine the winner of the game
        String result = determineWinner(userChoice, computerChoice);

        // Update the GUI labels
        computerChoiceLabel.setText("Computer's Choice: " + computerChoice);
        resultLabel.setText("Result: " + result);
    }

    private String determineWinner(String userChoice, String computerChoice) {
        // Implement the logic to determine the winner of the game
        if (userChoice.equals(computerChoice)) {
            return "Tie!";
        } else if ((userChoice.equals("Rock") && computerChoice.equals("Scissors")) ||
                (userChoice.equals("Paper") && computerChoice.equals("Rock")) ||
                (userChoice.equals("Scissors") && computerChoice.equals("Paper"))) {
            return "You win!";
        } else {
            return "Computer wins!";
        }
    }

    // Main method to run the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RPS game = new RPS();
            game.setVisible(true);
        });
    }
}

class CompetitiveLearningAlgorithm {
    private int numberOfNeurons;
    private double[] weights; // Array to store the weights of the neurons

    public CompetitiveLearningAlgorithm(int numberOfNeurons) {
        this.numberOfNeurons = numberOfNeurons;
        weights = new double[numberOfNeurons]; // Initialize the array to store weights

        // Initialize the weights randomly or with some initial values
        for (int i = 0; i < numberOfNeurons; i++) {
            weights[i] = Math.random(); // Initialize weights randomly between 0 and 1
        }
    }

    public void train(String userChoice) {
    // Implement the Competitive Learning Algorithm's training method
    // Map userChoice to the corresponding neuron and update its weight
    int neuronIndex = mapChoiceToNeuron(userChoice);
    
    // Adjust the weight of the winning neuron based on the user's choice
    for (int i = 0; i < numberOfNeurons; i++) {
        if (i == neuronIndex) {
            // Increment the weight of the winning neuron
            weights[i] += 0.1;
        } else {
            // Decrement the weights of the other neurons
            weights[i] -= 0.1;
        }

        // Ensure that the weights are within the range [0, 1]
        weights[i] = Math.max(0, Math.min(1, weights[i]));
    }
}

    public String predict() {
    // Implement the Competitive Learning Algorithm's prediction method
    // Return the choice corresponding to the neuron with the highest weight
    int neuronIndex = getWinningNeuronIndex();
    return mapNeuronToChoice(neuronIndex);
}

private int getWinningNeuronIndex() {
    // Implement a method to find the index of the neuron with the highest weight
    // Return the index of the winning neuron.
    int maxIndex = 0;
    for (int i = 1; i < numberOfNeurons; i++) {
        if (weights[i] > weights[maxIndex]) {
            maxIndex = i;
        }
    }
    return maxIndex;
}

private String mapNeuronToChoice(int neuronIndex) {
    // Implement a method to map the winning neuron index back to the game choice.
    // For example: 0 -> "Rock", 1 -> "Paper", 2 -> "Scissors"
    // Return the corresponding choice.
    switch (neuronIndex) {
        case 0:
            return "Rock";
        case 1:
            return "Paper";
        case 2:
            return "Scissors";
        default:
            throw new IllegalArgumentException("Invalid neuron index: " + neuronIndex);
    }
}


    private int mapChoiceToNeuron(String choice) {
        // Implement a method to map the user's choice (Rock/Paper/Scissors) to a neuron index
        // For example: "Rock" -> 0, "Paper" -> 1, "Scissors" -> 2
        // You can use a simple if-else or a HashMap to do the mapping.
        // Return the corresponding neuron index.
        if ("Rock".equals(choice)) {
            return 0;
        } else if ("Paper".equals(choice)) {
            return 1;
        } else if ("Scissors".equals(choice)) {
            return 2;
        } else {
            throw new IllegalArgumentException("Invalid choice: " + choice);
        }
    }
}
