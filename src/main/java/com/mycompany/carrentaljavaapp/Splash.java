package com.mycompany.carrentaljavaapp;

import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Splash extends JFrame {

    private JProgressBar progressBar;
    private JLabel titleLabel, iconLabel, loadingLabel;
    private Image backgroundImage;

    // الألوان المُوحدة
    private static final Color PROGRESS_COLOR = new Color(0, 150, 255);
    private static final Color TEXT_COLOR = new Color(230, 230, 230);
    private static final Color LOADING_TEXT_COLOR = new Color(180, 180, 180);

    public Splash() {
        FlatDarkLaf.setup();
        setUndecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(520, 380);
        setLocationRelativeTo(null);

        // ✅ تعيين أيقونة التطبيق في شريط العنوان
        setIconImage(loadImageSafely("/images/logo.png"));

        // ✅ تحميل صورة الخلفية
        backgroundImage = loadImageSafely("/images/car_bg.png");

        // --- البانل بخلفية الصورة ---
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                } else {
                    setBackground(new Color(30, 30, 30));
                }

                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(0, 0, 0, 120));
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();
            }
        };
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        // --- 1. العنوان المُركب مع الأيقونة ---
        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));

        JLabel titleIcon = new JLabel();
        titleIcon.setIcon(loadIconImageSafely("/images/car48.png", 27, 25));
        titlePanel.add(titleIcon);

        titleLabel = new JLabel("CAR RENTAL SYSTEM", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(PROGRESS_COLOR);
        titlePanel.add(titleLabel);
        titlePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- 2. الأيقونة المركزية الكبيرة ---
        iconLabel = new JLabel();
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        // يمكنك لاحقًا تعيين أيقونة هنا إن أردت، مثل:
        // iconLabel.setIcon(loadIconImageSafely("/images/logo.png", 100, 100));

        // --- نص حالة التحميل ---
        loadingLabel = new JLabel("Initializing Application...", SwingConstants.CENTER);
        loadingLabel.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        loadingLabel.setForeground(LOADING_TEXT_COLOR);
        loadingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- شريط التحميل ---
        progressBar = new JProgressBar();
        progressBar.setMaximum(100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        progressBar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        progressBar.setForeground(PROGRESS_COLOR);
        progressBar.setBackground(new Color(40, 40, 40));
        progressBar.putClientProperty("JComponent.roundRect", true);
        progressBar.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));

        // --- الترتيب ---
        panel.add(Box.createVerticalGlue());
        panel.add(titlePanel);
        panel.add(Box.createRigidArea(new Dimension(0, 35)));
        panel.add(iconLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 40)));
        panel.add(loadingLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(progressBar);
        panel.add(Box.createVerticalGlue());

        add(panel);
    }

    // ✅ دالة تحميل آمنة لصورة كـ Image
    private Image loadImageSafely(String path) {
        try {
            URL imageUrl = getClass().getResource(path);
            if (imageUrl != null) {
                return new ImageIcon(imageUrl).getImage();
            } else {
                System.err.println("❌ Image not found: " + path);
            }
        } catch (Exception e) {
            System.err.println("❌ Error loading image: " + e.getMessage());
        }
        return null;
    }

    // ✅ دالة تحميل صورة كـ ImageIcon مع إمكانية تغيير الحجم
    private ImageIcon loadIconImageSafely(String path, int width, int height) {
        Image img = loadImageSafely(path);
        if (img != null) {
            Image scaled = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaled);
        }
        return (ImageIcon) UIManager.getIcon("OptionPane.informationIcon");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Splash splash = new Splash();
            splash.setVisible(true);

            new Thread(() -> {
                try {
                    for (int i = 0; i <= 100; i++) {
                        Thread.sleep(50);
                        splash.progressBar.setValue(i);

                        if (i < 30) {
                            splash.loadingLabel.setText("Connecting to server...");
                        } else if (i < 70) {
                            splash.loadingLabel.setText("Loading core modules...");
                        } else if (i < 99) {
                            splash.loadingLabel.setText("Finalizing interface...");
                        } else {
                            splash.loadingLabel.setText("Ready!");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                SwingUtilities.invokeLater(() -> {
                    new Login().setVisible(true);
                    splash.dispose();
                });
            }).start();
        });
    }
}
