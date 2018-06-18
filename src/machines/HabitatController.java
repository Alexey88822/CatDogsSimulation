package machines;

import data.*;
import Commands.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;
import java.lang.*;
import java.util.Properties;
import Socket.*;

public class HabitatController {

    private Timer timer;
    private static HabitatView view;
    private HabitatModel model;
    private Properties properties;

    public HabitatController(HabitatView view, HabitatModel model) {
        this.view = view;
        this.model = model;
        properties = new Properties();
        try {
            properties.load(new FileInputStream(new File("ConfigurationFile.txt")));
        } catch (FileNotFoundException e) {
            try {
                defaulProp().store(new FileOutputStream(new File("ConfigurationFile.txt")), "Config");
                properties = defaulProp();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        init();
    }

    void setProp(Properties properties) {
        model.setTimeCatLife(Integer.parseInt(properties.getProperty("TimeCatLife")));
        view.timeCatLife.setText(String.valueOf(model.getTimeCatLife()));
        model.setTimeDogLife(Integer.parseInt(properties.getProperty("TimeDogLife")));
        view.timeDogLife.setText(String.valueOf(model.getTimeDogLife()));
        Cat.periodCat = Integer.parseInt(properties.getProperty("periodBornCat"));
        view.timeCatArea.setText(String.valueOf(Cat.periodCat));
        Dog.periodDog = Integer.parseInt(properties.getProperty("periodBornDog"));
        view.timeDogArea.setText(String.valueOf(Dog.periodDog));
        HabitatController.UpdateTimeArea(Dog.periodDog, Cat.periodCat);
        model.setpCat((double)(Integer.parseInt(properties.getProperty("probabilityCat")))/100);
        view.sliderCat.setValue(Integer.parseInt(properties.getProperty("probabilityCat")));
        HabitatController.UpdateCatPTextField();
        model.setpDog((double)(Integer.parseInt(properties.getProperty("probabilityDog")))/100);
        view.sliderDog.setValue(Integer.parseInt(properties.getProperty("probabilityDog")));
        HabitatController.UpdateDogPTextField();
        model.catAI.setPriority(Integer.parseInt(properties.getProperty("priorCat")));
        view.priorCatAI.setSelectedIndex(Integer.parseInt(properties.getProperty("priorCat"))-1);
        model.dogAI.setPriority(Integer.parseInt(properties.getProperty("priorDog")));
        view.priorDogAI.setSelectedIndex(Integer.parseInt(properties.getProperty("priorDog"))-1);
    }

    public static void Repaint(){
        view.repaint();
    }

    Properties defaulProp() {
        Properties prop = new Properties();
        prop.setProperty("TimeCatLife", "50");
        prop.setProperty("TimeDogLife", "50");
        prop.setProperty("periodBornCat", "1");
        prop.setProperty("periodBornDog", "1");
        prop.setProperty("probabilityCat", "70");
        prop.setProperty("probabilityDog", "70");
        prop.setProperty("priorCat", "1");
        prop.setProperty("priorDog", "1");
        return prop;
    }

    private void init() {
        setProp(properties);
        view.DogP.addTextListener(DogTextFieldList);
        view.DogP.addActionListener(DogTextFieldListener);
        view.DogP.setText(String.valueOf(model.getpDog()));
        view.CatP.addTextListener(CatTextFieldList);
        view.CatP.addActionListener(HeavyTextFieldListener);
        view.CatP.setText(String.valueOf(model.getpCat()));
        view.sliderCat.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int a = (view.sliderCat.getValue());
                if (a!=100) {
                    a=a%100;
                    double b = (double) a / 100;
                    String c = Double.toString(b);
                    view.CatP.setText(c);
                }
                else {
                    double b = (double) a / 100;
                    String c = Double.toString(b);
                    view.CatP.setText(c);
                }
            }
        });
        view.sliderDog.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int a = (view.sliderDog.getValue());
                if (a!=100) {
                    a=a%100;
                    double b = (double) a / 100;
                    String c = Double.toString(b);
                    view.DogP.setText(c);
                }
                else {
                    double b = (double) a / 100;
                    String c = Double.toString(b);
                    view.DogP.setText(c);
                }
            }
        });
        view.socketButton.addActionListener(onSocketClickListener);
        view.swapButton.addActionListener(onSwapClickListener);
        view.startButton.addActionListener(beginListner);
        view.endButton.addActionListener(endListner);
        view.yesButton.addActionListener(radioListener);
        view.noButton.addActionListener(radioListener);
        view.currentObjects.addActionListener(ObjectsListener);
        view.closeWindow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        view.startsim.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startSimulation(0,0,0);
            }
        });
        view.endsim.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                endSimulation();
            }
        });
        view.menuInformation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (view.showInfoCheckBox.isSelected()) {
                   view.infoArea.setVisible(false);
                   view.showInfoCheckBox.setSelected(false);
                } else {
                    view.showInfoCheckBox.setSelected(true);
                    view.infoArea.setVisible(true);
                }
            }
        });
        view.showInfoCheckBox.addItemListener(showInfoCheckBoxListener);
        view.panelGen.addKeyListener(keyAdapter);
        view.timeDogArea.addTextListener(timeDogTextFieldList);
        view.timeDogArea.addActionListener(timeDogTextFieldListener);
        view.timeDogArea.setText(String.valueOf(model.getTimeDog()));
        view.timeCatArea.addTextListener(timeCatTextFieldList);
        view.timeCatArea.addActionListener(timeCatTextFieldListener);
        view.timeCatArea.setText(String.valueOf(model.getTimeCat()));
        view.DogAIButton.addActionListener(DogAIListener);
        view.CatAIButton.addActionListener(CatAIListener);
        view.priorCatAI.addActionListener(PriorCatListener);
        view.priorDogAI.addActionListener(PriorDogListener);
        view.addWindowListener(WindowLis);
        view.save.addActionListener(SaveList);
        view.load.addActionListener(LoadList);
        view.console.addActionListener(consolLis);
    }

    private void startSimulation(long t, int _amountG, int _amountL) {
        float i = Float.parseFloat(view.DogP.getText());
        float j = Float.parseFloat(view.CatP.getText());
        if((i>1)||(i<0)||(j>1)||(j<0)) {
            Object[] options = {"Отмена",
                    "Окей"};
            int n = JOptionPane.showOptionDialog(new JFrame(),
                    "Данные были введены неверно!" + "\n" +
                    "Вероятность появления кошки: Число от 0 до 1" + "\n"
                    +"Вероятность появления собаки: Число от 0 до 1" + "\n"
                            + "Данные автоматически изменены на по умолчанию.",
                    "Ошибка ввода данных",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.ERROR_MESSAGE,
                    null,
                    options,
                    options[1]);
            if((i>1)||(i<0)) view.DogP.setText("0.5");
            if((j>1)||(j<0)) view.CatP.setText("0.5");
        }
        else {
            timer = new Timer();
            model.amountOfG = _amountG;
            model.amountOfL = _amountL;
            model.time = t;
            timer.schedule(new TimerTask() { //Добавление задания в таймер
                public void run() {
                    model.time++;
                    model.update(model.time);
                    String[] columns = {"Тип объекта", "Идентификатор", "Время рождения"};
                    String [] data[] = new String[1000][3];
                    for(int i=0;i<PetArrayList.getInstance().arrayPetList.size();i++){
                        Pet hh=PetArrayList.getInstance().arrayPetList.get(i);
                        if (hh instanceof Cat) data[i][0] = "Cat";
                        if (hh instanceof Dog) data[i][0] = "Dog";
                        data[i][1]=Integer.toString(hh.getIdentity());
                        data[i][2]=Long.toString(PetHashMap.getTimeofBorn(hh.getIdentity()));
                    }
                    JTable table=new JTable(data, columns);
                    view.dialog.setResizable(false);
                    view.dialog.remove(new JScrollPane(table));
                    view.dialog.add(new JScrollPane(table), BorderLayout.CENTER);
                    view.dialog.repaint(t,0 ,0,400,200);
                    if (view.yesButton.isSelected()) {
                        view.infoArea.setText(
                                "Количество: " + (model.amountOfL + model.amountOfG) + "\n" +
                                        "Собаки: " + model.amountOfL + "\n" +
                                        "Кошки: " + model.amountOfG + "\n" +
                                        "Время: " + model.time);
                    } else {
                        view.infoArea.setText(
                                "Количество: " + (model.amountOfL + model.amountOfG) + "\n" +
                                        "Собаки: " + model.amountOfL + "\n" +
                                        "Кошки: " + model.amountOfG);
                    }
                    view.startButton.setEnabled(false);
//                view.startSimulationItem.setEnabled(false);
                    //              view.endSimulationItem.setEnabled(true);
                    view.endButton.setEnabled(true);
                    view.repaint();
                }
            }, 0, 1000);
        }
    }

    public static void UpdateTimeArea(int a, int b){
        view.timeDogArea.setText(String.valueOf(a));
        view.timeCatArea.setText(String.valueOf(b));
    }

    private void endSimulation() {
        timer.cancel();
        timer.purge();
        HabitatModel.pauseCatAI();
        HabitatModel.pauseDogAI();
        if (view.showInfoCheckBox.isSelected()) {
            Object[] options = {"Отмена",
                    "Завершить"};
            int n = JOptionPane.showOptionDialog(new JFrame(),
                    "Количество: " + (model.amountOfL + model.amountOfG) + "\n" +
                            "Собаки: " + model.amountOfL + "\n" +
                            "Кошки: " + model.amountOfG + "\n" +
                            "Время: " + model.time,
                    "Прекращение работы программы",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[1]);
            if (n == 0) {
                startSimulation(model.time, model.amountOfG, model.amountOfL);

            } else {
                PetArrayList.getInstance().arrayPetList.clear();
                PetHashMap.hashmap.clear();
                PetTreeSet.numberSet.clear();
                view.repaint(); //"Очистка" интерфейса
                view.startButton.setEnabled(true);
                view.endButton.setEnabled(false);
            }
        } else {
            PetArrayList.getInstance().arrayPetList.clear();
            PetHashMap.hashmap.clear();
            PetTreeSet.numberSet.clear();
            view.repaint(); //"Очистка" интерфейса
            view.startButton.setEnabled(true);
            view.startSimulationItem.setEnabled(true);
            view.endSimulationItem.setEnabled(false);
            view.endButton.setEnabled(false);
        }
    }

    private ActionListener radioListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {


            switch (((JRadioButton) ae.getSource()).getText()) {
                case "Да":
                    view.infoArea.setText(
                            "Количество: " + (model.amountOfL + model.amountOfG) + "\n" +
                                    "Собаки: " + model.amountOfL + "\n" +
                                    "Кошки: " + model.amountOfG + "\n" +
                                    "Время: " + model.time);
                    break;
                case "Нет":
                    view.infoArea.setText(
                            "Количество: " + (model.amountOfL + model.amountOfG) + "\n" +
                                    "Собаки: " + model.amountOfL + "\n" +
                                    "Кошки: " + model.amountOfG);
                    break;
                default:
                    break;
            }
        }
    };

    private ItemListener showInfoCheckBoxListener = new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
                view.infoArea.setVisible(true);
            } else {//checkbox has been deselected
                view.infoArea.setVisible(false);
            }
        }
    };

    private ActionListener menuInfoListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (view.showInfoItem.getState()) {
                view.infoArea.setVisible(true);
                view.showInfoCheckBox.setSelected(true);
            } else {
                view.infoArea.setVisible(false);
                view.showInfoCheckBox.setSelected(false);
            }
        }
    };

    static void updateSliderCat(){
        double val1;
        int val2;
        val1 =Double.parseDouble(view.CatP.getText());
        val1 = val1*100;
        val2 = (int)val1;
        view.sliderCat.setValue(val2);
    }

    static void updateSliderDog(){
        double val3;
        int val4;
        val3=Double.parseDouble(view.DogP.getText());
        val3 = val3*100;
        val4 = (int)val3;
        view.sliderDog.setValue(val4);
    }

    static void UpdateDogPTextField(){
        int a = (view.sliderDog.getValue());
        if (a!=100) {
            a=a%100;
            double b = (double) a / 100;
            String c = Double.toString(b);
            view.DogP.setText(c);
        }
        else {
            double b = (double) a / 100;
            String c = Double.toString(b);
            view.DogP.setText(c);
        }
    }

 static void UpdateCatPTextField() {
     int a = (view.sliderCat.getValue());
     if (a != 100) {
         a = a % 100;
         double b = (double) a / 100;
         String c = Double.toString(b);
         view.CatP.setText(c);
     } else {
         double b = (double) a / 100;
         String c = Double.toString(b);
         view.CatP.setText(c);
     }
 }

    private KeyAdapter keyAdapter = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_B:
                    if (view.startButton.isEnabled()) {
                        startSimulation(0, 0, 0);
                    }
                    break;
                case KeyEvent.VK_E:
                    endSimulation();
                    break;
                case KeyEvent.VK_T:
                    if (view.yesButton.isSelected()) {
                        view.noButton.doClick();
                    }
                    else {
                        view.yesButton.doClick();
                    }
                    break;
                case KeyEvent.VK_O:
                    timer.cancel();
                    timer.purge();
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < PetArrayList.getInstance().arrayPetList.size(); i++) {
                        Pet pet = PetArrayList.getInstance().arrayPetList.get(i);
                        if (pet instanceof Cat) {
                            stringBuilder.append("Cat ");
                        } else {
                            stringBuilder.append("Dog ");
                        }
                        stringBuilder.append(pet.getIdentity() + " ");
                        stringBuilder.append(PetHashMap.getTimeofBorn(pet.getIdentity()));
                        stringBuilder.append("\n");
                    }
                    Object[] options = {"Resume"};
                    int n = JOptionPane.showOptionDialog(new JFrame(),
                            "Class| ID | TimeBorn" + "\n"+ stringBuilder,
                            "LiveObjects",
                            JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options,
                            options[0]);
                    if (n == 0) {
                        startSimulation(model.time, model.amountOfG, model.amountOfL);
                    }
                    break;
                case KeyEvent.VK_Y:
                    if (view.showInfoCheckBox.isSelected()) {
                        view.infoArea.setVisible(false);
                        view.showInfoCheckBox.setSelected(false);
                    } else {
                        view.infoArea.setVisible(true);
                        view.showInfoCheckBox.setSelected(true);
                    }
                    break;
            }
        }
    };

    private ActionListener ObjectsListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            timer.cancel();
            timer.purge();
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < PetArrayList.getInstance().arrayPetList.size(); i++) {
                Pet pet = PetArrayList.getInstance().arrayPetList.get(i);
                if (pet instanceof Cat) {
                    stringBuilder.append("Cat ");
                } else {
                    stringBuilder.append("Dog ");
                }
                stringBuilder.append(pet.getIdentity() + " ");
                stringBuilder.append(PetHashMap.getTimeofBorn(pet.getIdentity()));
                stringBuilder.append("\n");
            }
            Object[] options = {"Resume"};
            int n = JOptionPane.showOptionDialog(new JFrame(),
                    "Class| ID | TimeBorn" + "\n"+ stringBuilder,
                    "LiveObjects",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);
            if (n == 0) {
                startSimulation(model.time, model.amountOfG, model.amountOfL);
            }
        }
    };

    private ActionListener endListner = e -> endSimulation();

    private ActionListener beginListner = e -> startSimulation(0, 0, 0);

    private void timeCatValidation() {
        //Валидация формы
        try {
            Integer value;
            value = Integer.parseInt(view.timeCatArea.getText());
            if (value > 0) {
                model.setTimeCat(value);
            } else {
                throw new Exception();
            }
        } catch (Exception ignored) {

        }
    }

    private void timeDogValidation() {
        try { //Валидация формы
            Integer value;
            value = Integer.parseInt(view.timeDogArea.getText());
            if (value > 0) {
                model.setTimeDog(value);
            } else {
                throw new Exception();
            }
        } catch (Exception ignored) {

        }
    }

    private  void pDogValidation() {
        try { //Валидация формы
            Double value;
            value = Double.parseDouble(view.DogP.getText());
            if (value > 0 && value <= 1) {
                model.setpDog(value);
            } else {
                throw new Exception();
            }
        } catch (Exception ignored) {

        }
    }

    private void pCatValidation() {
        try { //Валидация формы
            Double value;
            value = Double.parseDouble(view.CatP.getText());
            if (value > 0 && value <= 1) {
                model.setpCat(value);
            } else {
                throw new Exception();
            }
        } catch (Exception ignored) {

        }
    }

    private ActionListener timeDogTextFieldListener = new ActionListener() { //Обработка по Enter
        @Override
        public void actionPerformed(ActionEvent e) {
            timeDogValidation();
            view.panelGen.requestFocus();
        }
    };

    private ActionListener timeCatTextFieldListener = new ActionListener() { //Обработка по Enter
        @Override
        public void actionPerformed(ActionEvent e) {
            timeCatValidation();
            view.panelGen.requestFocus();
        }
    };

    private ActionListener DogTextFieldListener = new ActionListener() { //Обработка по Enter
        @Override
        public void actionPerformed(ActionEvent e) {
            pDogValidation();
            view.panelGen.requestFocus();
        }
    };

    private ActionListener HeavyTextFieldListener = new ActionListener() { //Обработка по Enter
        @Override
        public void actionPerformed(ActionEvent e) {
            pCatValidation();
            view.panelGen.requestFocus();
        }
    };

    private ActionListener CatAIListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (model.catAI.paused){
                model.beginCatAI();
            }else {
                model.pauseCatAI();
            }
        }
    };



    private ActionListener DogAIListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (model.dogAI.paused){
                model.beginDogAI();
            }else {
                model.pauseDogAI();
            }
        }
    };

    private ActionListener PriorCatListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            model.catAI.setPriority(Integer.parseInt(String.valueOf(view.priorCatAI.getSelectedItem())));
            System.out.println("CatAI priority is: " + model.catAI.getPriority());
        }
    };

    private ActionListener PriorDogListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            model.dogAI.setPriority(Integer.parseInt(String.valueOf(view.priorDogAI.getSelectedItem())));
            System.out.println("DogAI priority is: " + model.dogAI.getPriority());
        }
    };

    private WindowListener WindowLis = new WindowListener() {
        @Override
        public void windowOpened(WindowEvent e) {
            int a = 0;
        }

        @Override
        public void windowClosing(WindowEvent e) {
            view.isGoing=false;
            properties.setProperty("TimeCatLife", view.timeCatLife.getText());
            properties.setProperty("TimeDogLife", view.timeDogLife.getText());
            properties.setProperty("periodBornCat", view.timeCatArea.getText());
            properties.setProperty("periodBornDog", view.timeDogArea.getText());
            properties.setProperty("probabilityCat", String.valueOf(view.sliderCat.getValue()));
            properties.setProperty("probabilityDog", String.valueOf(view.sliderDog.getValue()));
            properties.setProperty("priorCat", String.valueOf(model.catAI.getPriority()));
            properties.setProperty("priorDog", String.valueOf(model.dogAI.getPriority()));
            try {
                properties.store(new FileOutputStream(new File("ConfigurationFile.txt")), "Config");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.getWindow().setVisible(false);
            System.exit(0);
        }

        @Override
        public void windowClosed(WindowEvent e) {
            int a = 0;
        }

        @Override
        public void windowIconified(WindowEvent e) {
            int a = 0;
        }

        @Override
        public void windowDeiconified(WindowEvent e) {
            int a = 0;
        }

        @Override
        public void windowActivated(WindowEvent e) {
            int a = 0;
        }

        @Override
        public void windowDeactivated(WindowEvent e) {
            int a = 0;
        }
    };

    private ActionListener LoadList = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!view.startButton.isEnabled()) {
                endSimulation();
            }
            JFileChooser fileopen = new JFileChooser();
            int ret = fileopen.showDialog(null, "Открыть файл");
            if (ret == JFileChooser.APPROVE_OPTION) {
                File file = fileopen.getSelectedFile();
                try {
                    ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                    while (true) {
                        Object o = null;
                        try {
                            o = ois.readObject();
                        } catch (IOException | ClassNotFoundException ex) {
                            break;
                        }
                        if (!(o instanceof Pet)) {
                            continue;
                        }
                        Pet pet = (Pet) o;
                        if (pet instanceof Cat) {
                            model.amountOfG++;
                        } else if (pet instanceof Dog) {
                            model.amountOfL++;
                        }
                        System.out.println(pet.getIdentity());
                        PetArrayList.getInstance().arrayPetList.add(pet);
                        PetHashMap.hashmap.put(pet.getIdentity(), (long) 0);
                        PetTreeSet.numberSet.add(pet.getIdentity());
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                startSimulation(0,model.amountOfG, model.amountOfL);
                Repaint();
            }
        }
    };


    private ActionListener SaveList = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("SaveList.txt"));
                for(int i=0;i<PetArrayList.getInstance().arrayPetList.size();i++){
                    Pet hh = PetArrayList.getInstance().arrayPetList.get(i);
                    oos.writeObject(hh);
                }
            } catch (IOException ex1){
                ex1.printStackTrace();
            }
        }
    };

    private ActionListener consolLis = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Cons consol = new Cons(600, 400) {
                @Override
                public void command(String cmd) {
                    result = null;
                    if (cmd.equalsIgnoreCase("stop_simulation")) {
                        if (view.startButton.isEnabled()) {
                            result = "Симуляция не была начата.";
                        } else {
                            endSimulation();
                            result = "Симуляция была завершена.";
                        }
                    } else if (cmd.equalsIgnoreCase("begin_simulation")) {
                        if (!(view.startButton.isEnabled())) {
                            result = "Cимуляция в процессе.";
                        } else {
                            startSimulation(0,0,0);
                            result = "Симуляция была запущена.";
                        }
                    } else if (cmd.equalsIgnoreCase("menu_commands")) {
                        result = "begin_simulation - запуск симуляции;\n" +
                                "stop_simulation - завершение симуляции;";
                    }  else if (cmd.equalsIgnoreCase("current_time")) {
                    result = "Время работы программы:  " + model.time + " секунд.";
                    }
                    super.command(cmd);
                }
            };
        }
    };

    Socket socket;
    SocketRecieve socketRecieve;
    SocketSend socketSend;
    private boolean open;
    private ActionListener onSocketClickListener = e -> {
        String host = "localhost";
        int port = 8000;
        if (!open) {
            view.socketButton.setText("Закрыть");
            try {
                socket = new Socket(host, port);
                socketRecieve = new SocketRecieve(socket, view, model);
                socketRecieve.start();
                socketSend = new SocketSend(socket);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } else {
            view.socketButton.setText("Открыть");
            try {
                socket.getInputStream().close();
                socket.getOutputStream().close();
                socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        open = !open;
    };
    private ActionListener onSwapClickListener = e -> {
        try {
            socketSend.swap(PetArrayList.getInstance().users.get(view.usersList.getSelectedIndex()));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    };

    private TextListener timeDogTextFieldList = e -> timeDogValidation(); //Обработка текста во время ввода
    private TextListener timeCatTextFieldList = e -> timeCatValidation(); //Обработка текста во время ввода
    private TextListener CatTextFieldList = e -> pCatValidation(); //Обработка текста во время ввода
    private TextListener DogTextFieldList = e -> pDogValidation(); //Обработка текста во время ввода

}
