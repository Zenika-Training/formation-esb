<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:esb="http://esb.resanet.com"
	exclude-result-prefixes="xsl">
	<xsl:template match="/">
		<esb:requeteFiltree>
			<esb:data>
				<xsl:value-of select="/esb:requeteComplete/esb:data/text()" />
			</esb:data>
		</esb:requeteFiltree>
	</xsl:template>
</xsl:stylesheet>
