package fixmarkup;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class FixMarkup
{
	static int verbose = 1;

	public static void main(String[] args)
	{
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.open();

		File srcFile = new File("/Users/rkwright/dev/Sandbox_Util/FixMarkup/Crooked.html");

		if (verbose > 0)
			System.out.println("Replacing markup in " + srcFile.getPath());

		fixMarkup(srcFile);

		/*
		 * while (!shell.isDisposed()) { if (!display.readAndDispatch()) display.sleep(); }
		 */

		display.dispose();
	}

	public static void fixMarkup(File fiche)
	{

		BufferedReader reader = null;
		BufferedWriter writer = null;
		File newFiche;
		String curLine;
		String newLine = "";
		int n = 0;
		int m = 0;
		String leadMark = "_";
		String leadEm = "<i>";
		String trailEm = "</i>";
		boolean leading = true;

		try
		{

			reader = new BufferedReader(new FileReader(fiche.getPath()));
			newFiche = new File(fiche.getPath() + ".tmp");
			writer = new BufferedWriter(new FileWriter(newFiche.getPath()));

			while ((curLine = reader.readLine()) != null)
			{
				System.out.println("Replacing markup in " + curLine);

				m = n = 0;

				do
				{

					m = curLine.indexOf(leadMark, n);
					if (m != -1)
					{
						String frag = curLine.substring(n, m);
						newLine += frag;
						newLine += leading ? leadEm : trailEm;
						leading = !leading;
						n = m + 1;

					}
					else
					{
						String remain = curLine.substring(n);
						newLine += remain;
					}
				}
				while (m != -1);

				System.out.println("New line is " + newLine);
				newLine = "";

			}

			reader.close();
			writer.close();

			// fiche.delete();
			// newFiche.renameTo(fiche);

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
