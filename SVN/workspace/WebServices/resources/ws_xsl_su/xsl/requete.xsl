<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:hot="http://www.resanet.partenaires.com/hotel">
	<xsl:template match="/">
		<hot:trouverHotel>
			<hot:reference>
				<xsl:value-of select="//hot:reference" />
			</hot:reference>
		</hot:trouverHotel>
	</xsl:template>
</xsl:stylesheet>