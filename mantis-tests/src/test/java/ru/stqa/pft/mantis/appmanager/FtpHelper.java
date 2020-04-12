package ru.stqa.pft.mantis.appmanager;

import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FtpHelper {
  // для работы с капчей нужно отключить защиту на сервере тестового стенда
  // для этого нужно дописать спецю строчку в конфиг файле сервера (на удаленной машине),
  // для этого используем протокол FTP
  // необходимо старый конфигурационный файл сначала переименовать, подменить его новым,
  // а после выполнения тестов, восстанавливаем исходный конфиг файл на сервере

  private final ApplicationManager app;
  private final FTPClient ftp;

  public FtpHelper(ApplicationManager app) {
    //передаем хелперу ссылку на ApplicationManager
    this.app = app;
    //создаем FTPClient для установки соединения и передачи файлов
    ftp = new FTPClient();
  }

  // метод загружает новый файл, а старый переименовывает
  // file - локальный файл, который должен быть загружен на удаленный сервер
  // target - имя удаленного файла, куда все загружается
  // backup - имя резервной копии, если удаленный файл уже существует
  public void upload(File file, String target, String backup) throws IOException {
    //устанавливаем соединение с сервером
    ftp.connect(app.getProperty("ftp.host"));
    //логинимся
    ftp.login(app.getProperty("ftp.login"), app.getProperty("ftp.password"));
    //удаляем предыдущую резервную копию
    ftp.deleteFile(backup);
    //переименовываем удаленный файл (делаем резервную копию)
    ftp.rename(target, backup);
    //включаем пассивный режим передачи данных
    ftp.enterLocalPassiveMode();
    //из локального файла делаем InputStream(для чтения бинарных данных),
    // эти данные читаются из локального файла, передаются на удаленную машину,
    // где сохраняются в удаленном файле target
    ftp.storeFile(target, new FileInputStream(file));
    //разрываем соединение
    ftp.disconnect();
  }

  //метод восстанавливает исходный конфиг файл тестируемой системы
  public void restore(String backup, String target) throws IOException {
    //устанавливаем соединение с сервером
    ftp.connect(app.getProperty("ftp.host"));
    //логинимся
    ftp.login(app.getProperty("ftp.login"), app.getProperty("ftp.password"));
    //удаляем загруженный нами файл
    ftp.deleteFile(target);
    //восстанавливаем оригинальный файл из резервной копии
    ftp.rename(backup, target);
    //разрываем соединение
    ftp.disconnect();
  }
}
