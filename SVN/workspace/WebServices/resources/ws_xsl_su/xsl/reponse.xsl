<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:hot="http://www.resanet.partenaires.com/hotel">
	<xsl:template match="/">
		<rechercherHotelResponse xmlns="http://www.resanet.partenaires.com/hotel">
			<hotel>
				<nom><xsl:value-of select="//hot:name" /></nom>
				<ville><xsl:value-of select="//hot:city" /></ville>
				<nbEtoiles><xsl:value-of select="//hot:hotel/@stars" /></nbEtoiles>
			</hotel>
		</rechercherHotelResponse>
	</xsl:template>
</xsl:stylesheet>