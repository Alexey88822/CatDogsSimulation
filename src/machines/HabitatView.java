package machines;

import data.Pet;
import data.Fon;
import data.PetArrayList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.imageio.ImageIO;
import javax.swing.border.Border;
import java.io.File;
import java.io.IOException;

public class HabitatView extends JFrame{

    private int wHeight;
    private int wLength;
    private int wPosX;
    private int wPosY;
    public static boolean isGoing = true;

    JMenuItem startSimulationItem = null;
    JMenuItem endSimulationItem = null;
    JCheckBoxMenuItem showInfoItem = null;

    JPanel mainPanel = null;
    public static JPanel panelGen = null;
    JButton startButton = null;
    JButton endButton = null;
    JCheckBox showInfoCheckBox = null;
    JTextArea infoArea = null;
    JRadioButton yesButton = null;
    JRadioButton noButton = null;
    JLabel showTimeLabel = null;
    JMenuBar Menubar = null;
    JMenuItem closeWindow = null;
    JMenuItem startsim = null;
    JMenuItem endsim = null;
    JMenuItem menuInformation = null;
    JMenuItem load = null;
    JMenuItem save = null;

    JLabel timeCatLabel = null;
    JLabel timeDogLabel = null;
    JLabel pTimeCatLabel = null;
    JLabel pTimeDogLabel = null;
    TextField timeCatArea = null;
    TextField timeDogArea = null;

    static TextField CatP = null;
    static TextField DogP = null;
    static TextField timeCatLife = null;
    static TextField timeDogLife = null;
    JLabel timeCatLifeLabel = null;
    JLabel timeDogLifeLabel = null;
    JButton currentObjects = null;
    JDialog dialog = null;

    JSlider sliderCat = null;
    JSlider sliderDog = null;

    JLabel p1Slider = null;
    JLabel p2Slider = null;

    JButton CatAIButton=null;
    JButton DogAIButton=null;
    JComboBox priorCatAI=null;
    JComboBox priorDogAI=null;
    JButton console=null;
    JList usersList=null;
    DefaultListModel listModel=null;
    JButton socketButton=null;
    JButton swapButton=null;

    public HabitatView(int wLength, int wHeight, int wPosX, int wPosY) {
        this.wLength = wLength;
        this.wHeight = wHeight;
        this.wPosX = wPosX;
        this.wPosY = wPosY;
        drawUI();
    }

    public static void drawPets(Graphics g){
        for (int i = 0; i < PetArrayList.getInstance().arrayPetList.size(); i++) {
            Pet hh = PetArrayList.getInstance().arrayPetList.get(i);
            hh.paint(g);
        }
    }

    private void drawUI() {
        GridBagLayout gr = new GridBagLayout();
        setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);
        Font font = new Font("Georgia", Font.BOLD|Font.ITALIC, 9);

        mainPanel = new JPanel();
        mainPanel.setLayout(gr);
        mainPanel.setBackground(Color.LIGHT_GRAY);
        panelGen = new JPanel()  {
            @Override
            protected void paintComponent(Graphics g) { //Необходимо при перерисовки интерфейса
                super.paintComponent(g);
                Fon.paintfon(g);
                for (Pet pet : PetArrayList.getInstance().arrayPetList) {
                    pet.paint(g);
                }
            }
        };
        add(panelGen, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.EAST);

        Menubar = new JMenuBar();

        setJMenuBar(Menubar);

        startsim = new JMenuItem("Начать симуляцию");
        startsim.setFont(new Font("Times New Roman", Font.BOLD, 14));
        Menubar.add(startsim);

        endsim = new JMenuItem("Завершить симуляцию");
        endsim.setFont(new Font("Times New Roman", Font.BOLD, 14));
        Menubar.add(endsim);

        menuInformation = new JMenuItem("Показать/скрыть информацию");
        menuInformation.setFont(new Font("Times New Roman", Font.BOLD, 14));
        Menubar.add(menuInformation);

        save = new JMenuItem("Сохранить");
        save.setFont(new Font("Times New Roman", Font.BOLD, 14));
        Menubar.add(save);

        load = new JMenuItem("Загрузить");
        load.setFont(new Font("Times New Roman", Font.BOLD, 14));
        Menubar.add(load);

        closeWindow = new JMenuItem("Выйти");
        closeWindow.setFont(new Font("Times New Roman", Font.BOLD, 14));
        Menubar.add(closeWindow);

        startButton = new JButton("Старт");
        startButton.setFont(font);
        startButton.setSize(20, 25);

        endButton = new JButton("Стоп");
        endButton.setFont(font);
        endButton.setSize(20, 25);
        endButton.setEnabled(false);

        dialog = new JDialog(this, "Список объектов", true);
        dialog.setSize(400,200);
        dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        showInfoCheckBox = new JCheckBox("Показать информацию");
        showInfoCheckBox.setFont(new Font("Georgia", Font.BOLD|Font.ITALIC, 12));
        showInfoCheckBox.setFocusable(false);
        showInfoCheckBox.setSelected(true);

        showTimeLabel = new JLabel("Показать время?");
        showTimeLabel.setFont(new Font("Georgia", Font.BOLD|Font.ITALIC, 12));

        yesButton = new JRadioButton("Да");
        yesButton.setFont(new Font("Georgia", Font.BOLD|Font.ITALIC, 12));
        yesButton.setFocusable(false);

        noButton = new JRadioButton("Нет");
        noButton.setFont(new Font("Georgia", Font.BOLD|Font.ITALIC, 12));
        noButton.setFocusable(false);
        noButton.setSelected(true);

        ButtonGroup group = new ButtonGroup();
        group.add(yesButton);
        group.add(noButton);

        infoArea = new JTextArea();
        infoArea.setEditable(false);
        infoArea.setVisible(true);
        infoArea.setFocusable(false);
        infoArea.setText(
                "Количество: " + (HabitatModel.amountOfL + HabitatModel.amountOfG) + "\n" +
                        "Собаки: " + HabitatModel.amountOfL + "\n" +
                        "Кошки: " + HabitatModel.amountOfG);

        timeCatLabel = new JLabel("N1 (Период появления кошки):");
        timeCatLabel.setFont(new Font("Georgia", Font.BOLD|Font.ITALIC, 12));
        timeCatArea = new TextField();
        timeCatArea.setText("1");
        timeCatArea.setBounds(900, 0, 50, 20);

        timeDogLabel = new JLabel("N2 (Период появления собаки):");
        timeDogLabel.setFont(new Font("Georgia", Font.BOLD|Font.ITALIC, 12));
        timeDogLabel.setBounds(620, 30, 240, 20);
        timeDogArea = new TextField();
        timeDogArea.setText("1");
        timeDogArea.setBounds(900, 30, 50, 20);

        pTimeCatLabel = new JLabel("P1 (Вероятность появления кошки):");
        pTimeCatLabel.setFont(new Font("Georgia", Font.BOLD|Font.ITALIC, 12));
        pTimeCatLabel.setBounds(620, 55, 260, 20);
        CatP = new TextField("0.5");
        CatP.setBounds(900, 55, 50, 20);

        pTimeDogLabel = new JLabel("P2 (Вероятность появления собаки):");
        pTimeDogLabel.setFont(new Font("Georgia", Font.BOLD|Font.ITALIC, 12));
        pTimeDogLabel.setBounds(620, 80, 260, 20);
        DogP = new TextField("0.5");
        DogP.setBounds(900, 80, 50, 20);

        p1Slider = new JLabel("P1 (Вероятность появления кошки):");
        p1Slider.setFont(new Font("Georgia", Font.BOLD|Font.ITALIC, 12));
        p1Slider.setBounds(430, 0, 20, 20);

        sliderCat = new JSlider(JSlider.HORIZONTAL,0,100,10);
        sliderCat.setBounds(450,0,160,50);
        sliderCat.setMinorTickSpacing(5);
        sliderCat.setMajorTickSpacing(20);
        sliderCat.setPaintTicks(true);
        sliderCat.setPaintLabels(true);
        sliderCat.setValue(50);

        p2Slider = new JLabel("P2 (Вероятность появления собаки):");
        p2Slider.setFont(new Font("Georgia", Font.BOLD|Font.ITALIC, 12));
        p2Slider.setBounds(430, 50, 20, 20);

        sliderDog = new JSlider(JSlider.HORIZONTAL,0,100,10);
        sliderDog.setBounds(450,50,160,50);
        sliderDog.setMinorTickSpacing(5);
        sliderDog.setMajorTickSpacing(20);
        sliderDog.setPaintTicks(true);
        sliderDog.setPaintLabels(true);
        sliderDog.setValue(50);

        timeCatLifeLabel = new JLabel("Время жизни кошек:");
        timeCatLifeLabel.setFont(new Font("Georgia", Font.BOLD|Font.ITALIC, 12));

        timeDogLifeLabel = new JLabel("Время жизни собак:");
        timeDogLifeLabel.setFont(new Font("Georgia", Font.BOLD|Font.ITALIC, 12));

        timeCatLife = new TextField();
        timeCatLife.setText("500");

        timeDogLife = new TextField();
        timeDogLife.setText("500");

        currentObjects = new JButton("Текущие объекты");
        currentObjects.setFont(font);

        CatAIButton = new JButton("Activate AI Cat");
        DogAIButton = new JButton("Activate AI Dog");
        priorCatAI = new JComboBox();

        for(int i=1; i<=10; i++){
            priorCatAI.addItem(i);
        }

        priorDogAI = new JComboBox();

        for(int i =1; i<=10;i++){
            priorDogAI.addItem(i);
        }
        socketButton = new JButton("Открыть сокеты");
        swapButton = new JButton("Swap");
        listModel = new DefaultListModel();
        usersList = new JList(listModel);

        console = new JButton("Консоль");

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridwidth = 2;
        constraints.ipady = 40;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        gr.setConstraints(startButton, constraints);
        mainPanel.add(startButton);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.ipady = 0;
        gr.setConstraints(showInfoCheckBox, constraints);
        mainPanel.add(showInfoCheckBox);
        constraints.gridwidth = GridBagConstraints.RELATIVE;
        gr.setConstraints(showTimeLabel, constraints);
        mainPanel.add(showTimeLabel);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        gr.setConstraints(yesButton, constraints);
        mainPanel.add(yesButton);
        constraints.gridwidth = GridBagConstraints.RELATIVE;
        gr.setConstraints(infoArea, constraints);
        mainPanel.add(infoArea);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        gr.setConstraints(noButton, constraints);
        mainPanel.add(noButton);
        constraints.gridwidth = GridBagConstraints.RELATIVE;
        gr.setConstraints(timeCatLabel, constraints);
        mainPanel.add(timeCatLabel);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        gr.setConstraints(timeCatArea, constraints);
        mainPanel.add(timeCatArea);
        constraints.gridwidth = GridBagConstraints.RELATIVE;
        gr.setConstraints(timeDogLabel, constraints);
        mainPanel.add(timeDogLabel);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.anchor = GridBagConstraints.CENTER;
        gr.setConstraints(timeDogArea, constraints);
        mainPanel.add(timeDogArea);
        constraints.gridwidth = GridBagConstraints.RELATIVE;
        gr.setConstraints(pTimeCatLabel, constraints);
        mainPanel.add(pTimeCatLabel);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        gr.setConstraints(CatP, constraints);
        mainPanel.add(CatP);
        constraints.gridwidth = GridBagConstraints.RELATIVE;
        gr.setConstraints(pTimeDogLabel, constraints);
        mainPanel.add(pTimeDogLabel);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        gr.setConstraints(DogP, constraints);
        mainPanel.add(DogP);
        constraints.gridwidth = GridBagConstraints.RELATIVE;
        gr.setConstraints(p1Slider, constraints);
        mainPanel.add(p1Slider);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        gr.setConstraints(sliderCat, constraints);
        mainPanel.add(sliderCat);
        constraints.gridwidth = GridBagConstraints.RELATIVE;
        gr.setConstraints(p2Slider, constraints);
        mainPanel.add(p2Slider);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        gr.setConstraints(sliderDog, constraints);
        mainPanel.add(sliderDog);
        constraints.ipady = 20;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        gr.setConstraints(currentObjects, constraints);
        mainPanel.add(currentObjects);
        constraints.ipady = 0;
        constraints.gridwidth = GridBagConstraints.RELATIVE;
        gr.setConstraints(timeCatLifeLabel, constraints);
        mainPanel.add(timeCatLifeLabel);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        gr.setConstraints(timeCatLife, constraints);
        mainPanel.add(timeCatLife);
        constraints.gridwidth = GridBagConstraints.RELATIVE;
        gr.setConstraints(timeDogLifeLabel, constraints);
        mainPanel.add(timeDogLifeLabel);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        gr.setConstraints(timeDogLife, constraints);
        mainPanel.add(timeDogLife);
        constraints.gridwidth = GridBagConstraints.RELATIVE;
        gr.setConstraints(CatAIButton, constraints);
        mainPanel.add(CatAIButton);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        gr.setConstraints(DogAIButton, constraints);
        mainPanel.add(DogAIButton);
        constraints.gridwidth = GridBagConstraints.RELATIVE;
        gr.setConstraints(priorCatAI, constraints);
        mainPanel.add(priorCatAI);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        gr.setConstraints(priorDogAI, constraints);
        mainPanel.add(priorDogAI);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        gr.setConstraints(console, constraints);
        mainPanel.add(console);
        constraints.gridwidth = GridBagConstraints.RELATIVE;
        gr.setConstraints(socketButton, constraints);
        mainPanel.add(socketButton);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        gr.setConstraints(swapButton, constraints);
        mainPanel.add(swapButton);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        gr.setConstraints(usersList, constraints);
        mainPanel.add(usersList);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.ipady = 40;
        gr.setConstraints(endButton, constraints);
        mainPanel.add(endButton);

        panelGen.setFocusable(true); //Разрешить обработку клавиш
        setBounds(wPosX, wPosY, wLength, wHeight);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void updateUsersList() {
        listModel.clear();
        for(int i=0; i<PetArrayList.getInstance().users.size(); i++) {
            listModel.addElement(PetArrayList.getInstance().users.get(i));
        }
    }
}
