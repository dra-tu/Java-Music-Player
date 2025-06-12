package gui;

import gui.color.ColorMgr;
import gui.color.JmpColored;
import gui.color.JmpGuiColorPalette;
import gui.color.palettes.DefaultColorPalette;
import gui.color.palettes.NewColorPalette;

import gui.elements.bottomBar.buttons.backButton.BackButton;
import gui.elements.bottomBar.buttons.nextButton.NextButton;
import gui.elements.bottomBar.buttons.pauseButton.PauseButton;
import gui.elements.bottomBar.timeBar.TimeBar;
import gui.elements.mainArea.lists.allSongList.AllSongList;
import gui.elements.mainArea.lists.historyList.HistoryList;
import gui.elements.mainArea.volumeSlider.VolumeSlider;
import gui.elements.mainArea.volumeSlider.VolumeSliderType;
import gui.elements.sideBar.colorCange.ColorChangeButton;
import gui.elements.sideBar.reloadButton.ReloadButton;
import gui.playMode.AutoPlay;
import musicPlayer.MusicPlayer;
import musicPlayer.PlayerStarter;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class Gui extends JFrame implements PlayerStarter, JmpColored {
    static public final String RESOURCES_PATH = "/resources/";
    static public final String IMG_PATH = RESOURCES_PATH + "img/";
    private static final int height = 600;
    private static final int width = 1_000;

    private ColorMgr colorMgr;

    private JPanel sideBar;
    private JPanel mainArea;
    private JPanel bottomBar;

    public Gui() {
        super();
        this.colorMgr = new ColorMgr();

        setFrameValues();
        createSkeleton();

        setVisible(true);
    }

    public JPanel getBottomBar() {
        return bottomBar;
    }
    public JPanel getMainArea() {
        return mainArea;
    }
    public JPanel getSideBar() {
        return sideBar;
    }

    @Override
    public boolean start(String dirPath) {
        MusicPlayer musicPlayer = new MusicPlayer();
        try {
            musicPlayer.useDir(dirPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        colorMgr.addPaletteToList(new DefaultColorPalette());
        colorMgr.addPaletteToList(new NewColorPalette());
        colorMgr.changeColor("Default");
        colorMgr.add(this);

        this.createGuiElements(musicPlayer);

        new AutoPlay(musicPlayer, true);
        musicPlayer.startRandomSong();

        return true;
    }

    private void setFrameValues() {
        // set icon
        URL iconURL = getClass().getResource(Gui.IMG_PATH + "icon.png");
        if (iconURL != null) setIconImage(new ImageIcon(iconURL).getImage());

        // set title
        setTitle("jmp [Java Music Player]");

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setSize(width, height);//400 width and 500 height

        setResizable(false);

        setLayout(new GridBagLayout());
    }

    private void createSkeleton() {
        GridBagConstraints gbc = new GridBagConstraints();

        sideBar = new JPanel();
        sideBar.setLayout(new BoxLayout(sideBar, BoxLayout.Y_AXIS));

        mainArea = new JPanel();
        bottomBar = new JPanel();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.1;
        gbc.weighty = 0.8;
        gbc.fill = GridBagConstraints.BOTH;
        add(sideBar, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.9;
        gbc.weighty = 0.8;
        gbc.fill = GridBagConstraints.BOTH;
        add(mainArea, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 0.2;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.BOTH;
        add(bottomBar, gbc);
    }

    public void createGuiElements(MusicPlayer musicPlayer) {
        bottomBar.add(new TimeBar(colorMgr, musicPlayer));
        bottomBar.add(new PauseButton(colorMgr, musicPlayer));
        bottomBar.add(new NextButton(colorMgr, musicPlayer));
        bottomBar.add(new BackButton(colorMgr, musicPlayer));

        mainArea.add(new AllSongList(colorMgr, musicPlayer));
        mainArea.add(new HistoryList(colorMgr, musicPlayer));
        mainArea.add(new VolumeSlider("Song", VolumeSliderType.SONG_VOLUME, colorMgr, musicPlayer));
        mainArea.add(new VolumeSlider("Default", VolumeSliderType.DEFAULT_VOLUME, colorMgr, musicPlayer));

        sideBar.add(new ReloadButton(colorMgr, musicPlayer, this));
        sideBar.add(new ColorChangeButton(colorMgr));
    }

    public void removeGuiElements() {
        bottomBar.removeAll();
        mainArea.removeAll();
        sideBar.removeAll();
        colorMgr.removeAll();

        colorMgr.add(this);
    }

    @Override
    public <CP extends JmpGuiColorPalette> void updateColors(CP cp) {
        // Skeleton
        sideBar.setBackground(cp.getSideBar());
        mainArea.setBackground(cp.getMainArea());
        bottomBar.setBackground(cp.getBottomBar());
    }
}