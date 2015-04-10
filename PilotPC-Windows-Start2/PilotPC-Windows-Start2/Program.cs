using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Windows.Forms;

namespace PilotPC_Windows_Start2
{
    static class Program
    {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            var info=new ProcessStartInfo();
            string plik = "javaw.exe";
            string arg = "-jar ";
            var pliki = new FileInfo("java\\pilotpc.jar");
            var exe = new FileInfo(System.Reflection.Assembly.GetExecutingAssembly().GetModules()[0].FullyQualifiedName);
            info.WorkingDirectory = exe.DirectoryName;
            if (pliki.Exists)
                arg += "\"" + pliki.FullName + "\"";
            else
            {
                pliki = new FileInfo("pilotpc.jar");
                if (pliki.Exists)
                    arg += "\"" + pliki.FullName + "\"";
                else
                {
                    pliki = new FileInfo(info.WorkingDirectory+"\\pilotpc.jar");
                    if (pliki.Exists)
                        arg += "\"" + pliki.FullName + "\"";
                    else
                    {
                        pliki = new FileInfo(info.WorkingDirectory + "\\java\\pilotpc.jar");
                        if (pliki.Exists)
                            arg += "\"" + pliki.FullName + "\"";

                        MessageBox.Show("Brak pliku pilotpc.jar", "Jaebe PilotPC", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    }
                }
            }
            bool okno = true;
            var args = Environment.GetCommandLineArgs();
            for (var i = 1; args.Length > i; i++)
            {
                if (args[i] == "-k" || args[i] == "--k" || args[i] == "\\k")
                    plik = "java.exe";
                else if (args[i] == "-no" || args[i] == "--no" || args[i] == "\\no")
                    okno = false;
                else
                    arg += " " + args[i];

            }
            if (okno)
                arg += " -o";
            if ((new FileInfo(plik)).Exists)
                info.FileName = (new FileInfo(plik)).FullName;
            else if ((new FileInfo(Environment.GetFolderPath(Environment.SpecialFolder.System) + "\\" + plik)).Exists)
                info.FileName = (new FileInfo(Environment.GetFolderPath(Environment.SpecialFolder.System) + "\\" + plik)).FullName;
            else if ((new FileInfo(Environment.GetFolderPath(Environment.SpecialFolder.CommonProgramFiles) + "\\" + plik)).Exists)
                info.FileName = (new FileInfo(Environment.GetFolderPath(Environment.SpecialFolder.CommonProgramFiles) + "\\" + plik)).FullName;
            else
                info.FileName = plik;
               // MessageBox.Show("Nie znaleziono javy", "Jaebe PilotPC", MessageBoxButtons.OK, MessageBoxIcon.Error);
            info.Arguments=arg;
            
            var proc = Process.Start(info);

            // Application.Run(new Form1());
        }
    }
}
