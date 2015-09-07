package com.resanet.esb.common.file;

import java.io.File;
import java.io.FileFilter;

public class ResanetFileFilter implements FileFilter {

	/**
	 * Impl�mentation de la m�thode qui filtre via le nom du fichier.
	 */
	public boolean accept(File pathname) {
		if (pathname.getName().startsWith("Resanet-")) {
			return true;
		}
		return false;
	}
}
