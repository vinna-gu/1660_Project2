import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.util.*;
/* note to self: currently there is some sort of bug where if i decide to choose one file, 
   i'll have to choose it again bc it doesn't show up but either way, it still works dude. 

   do u want to create a back button for every panel???
   also do you want to handle the case where a user doesn't select a file??
   hmmmmmmmm 
   thats a good idea current vinna
   i'll let future vinna handle this
   im 100% procrastinating
   hopefully i'll be smart enough to remove this or suffer the consequences AKA shame
*/

class GUI extends JFrame implements ActionListener{
    private ArrayList<File> filePathsArr;   // storing the filepaths in here for rn
    private JButton fileButton, indButton, searchButton, topNButton, backButton, searchTermButton;
    private JFrame frame;
    private JLabel displayFiles;
    private JPanel startPanel = new JPanel();
    private JPanel indicesPanel = new JPanel();
    private JPanel searchPanel = new JPanel();
    private JPanel topNPanel = new JPanel();
    private JScrollPane jScrollPane1;
    private JTextArea textBox;
    private String searchTerm;

    public GUI () {
        // Create Frame
        frame = new JFrame("Project Option 2: Vinna's Search Engine");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLayout(null);

        // putting MyName Search Engine
        JLabel name = new JLabel("Vinna's Search Engine");
        name.setBounds(5, 5, 150, 20);
        frame.add(name);
 
        // need to create a startPanel to hold all this info
        startPanel.setSize(600, 600);
        startPanel.setLayout(null);

        // Load Engine
        JLabel loadEngine = new JLabel("Load My Engine");
        loadEngine.setFont(new Font("Arial", Font.BOLD, 20));
        loadEngine.setBounds(225,150,200,50);
        startPanel.add(loadEngine);

        // Choose Files Button
        fileButton = new JButton("Choose Files");
        fileButton.setBounds(225,200,150,50);
        fileButton.addActionListener(this);
        startPanel.add(fileButton);


        // Inverted Indices Button
        indButton = new JButton("Construct Inverted Indices");
        indButton.setBounds(200,350,200,50);
        indButton.addActionListener(this);
        startPanel.add(indButton);

        // add that panel to the frame
        frame.add(startPanel);
        startPanel.setVisible(true);
    }

    // to display GUI, set it to visible
    public void runGui() {
        frame.setVisible(true);

        // that's right. i blocked users from resizing this window so i don't have to bother handling resizing!! who wants to do that! not me!
        frame.setResizable(false);
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if(command.equals("Choose Files")) {
            pickFiles();
        }
        else if(command.equals("Construct Inverted Indices")) {
            // lets start with removing the first panel then calling the method
            startPanel.setVisible(false);
            // startPanel.removeAll();
            // startPanel.repaint();
            // startPanel.revalidate();
            invertedIndices();
        }
        else if(command.equals("Search for Term")) {
            System.out.println("aw yeah let's search for a term");
            indicesPanel.setVisible(false);
            // indicesPanel.removeAll();
            // indicesPanel.repaint();
            // indicesPanel.revalidate();
            searchTerm();
        }
        else if(command.equals("Top-N")) {
            System.out.println("interesting...i have to do all this work");
        }
        else if(command.equals("Back")) {
            if(searchPanel.isVisible()) {
                prevPanel(searchPanel);
            }
        }
    }

    // call this to display a new panel each time a button is pressed
    private void displayNewPanel(JPanel panelName) {
        // initializing the size and giving it the ability to display
        panelName.setSize(600, 600);
        panelName.setLayout(null);
        panelName.repaint();
        panelName.revalidate();
        panelName.setVisible(true);
        frame.add(panelName);
    }

    private void prevPanel(JPanel panelName) {
        panelName.setVisible(false);
        indicesPanel.setVisible(true);
    }
    // PICKING FILES
    private void pickFiles() {
        // user chooses some file(s) with multiselection so they can choose more than one file if wanted
        JFileChooser chooseFiles = new JFileChooser();
        chooseFiles.setMultiSelectionEnabled(true);
        chooseFiles.setCurrentDirectory(new File("./Data"));    // so i don't have to keep searching for data folder. im too lazy
        chooseFiles.setSelectedFile(new File(""));
        chooseFiles.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        if (chooseFiles.showOpenDialog(frame) == JFileChooser.OPEN_DIALOG) {
            // store files into an array
            File[] openFiles = chooseFiles.getSelectedFiles();

            // Case: if user wants to reselect files, check: if displayFiles isn't empty, set to empty
            if(displayFiles != null) {
                displayFiles.setText("");
            }
            displayFiles = new JLabel();
            String appendText = "";
            filePathsArr = new ArrayList<File>();

            // getting the file names we are using
            for(int i = 0; i < openFiles.length; i++) {
                // contains filepath
                String filename = openFiles[i].toString();
                filePathsArr.add(openFiles[i]);

                // removing filepath and getting only the name
                int index = filename.lastIndexOf('\\'); // windows
                if (index < 0) {
                    index = filename.lastIndexOf('/');  // mac
                }
                String outputFile = filename.substring(index+1, filename.length());
                appendText = appendText + outputFile +"<br/>";

                // display text to GUI
                displayFiles.setText("<html>" + appendText + "</html>");
                displayFiles.setBounds(225,250,500,75);
                startPanel.add(displayFiles);

            }
        } else {
            System.out.println("Exited without choosing files!");
        }

        System.out.println("These are the files chosen: " + filePathsArr); // ~~delete this~~
    }

    // DOING INVERTED INDICES
    private void invertedIndices() {
        // using the indicesPanel now
        JLabel loadEngine = new JLabel("<html>Engine was loaded &<br/>Inverted indicies were constructed successfully!</html>");
        loadEngine.setFont(new Font("Arial", Font.BOLD, 20));
        loadEngine.setBounds(190,-150,300,500);
        indicesPanel.add(loadEngine);

        // I WANT TO TAKE A NAP
        searchButton = new JButton("Search for Term");
        topNButton = new JButton("Top-N");
        searchButton.setBounds(225,200,150,50);
        searchButton.addActionListener(this);

        topNButton.setBounds(225,300,150,50);
        topNButton.addActionListener(this);

        // add zee buttons to zee panel 
        indicesPanel.add(searchButton);
        indicesPanel.add(topNButton);

        displayNewPanel(indicesPanel);
    }

    // TIME TO SEARCH FOR A TERM o_O
    private void searchTerm() {
        JLabel searchLabel = new JLabel("<html>Enter Your Search Term</html>");
        searchLabel.setFont(new Font("Arial", Font.BOLD, 20));
        // searchLabel.setBounds(190,-150,300,500);
        searchLabel.setBounds(190, -100, 300, 500);
        searchPanel.add(searchLabel);

        // incorporating a back button
        backButton = new JButton("Back");
        backButton.setBounds(10, 30, 100, 50);
        backButton.addActionListener(this);

        // Giving user ability to enter serach term
        JTextField searchField =new JTextField();  
        searchField.setBounds(200, 200, 200,30); 
        searchTermButton = new JButton("Search");
        searchTermButton.setBounds(400, 200, 100, 30);

        searchTerm = searchField.getText();
        System.out.println(searchTerm);

        // add zee buttons to zee panel 
        searchPanel.add(backButton);
        searchPanel.add(searchField); 
        searchPanel.add(searchTermButton);

        displayNewPanel(searchPanel);



    }
}