To deploy project you have configuration:

1. upload.location in web.xml - is a path to folder with fotos of recepts on your computer
	(put 'foto' folder to this path)
2. resource.xml.location in web.xml - is a path to folder with resource files for xml-pdf proccessing
	(put 'xml' folder to this path)
3. file Lang.xml in 'xml' folder contain path to font for pdf creation
	(change this path to your chosen cyrillic font)
4. file recept.xsl contain reference to file name which generated in code
	(don't change this name)