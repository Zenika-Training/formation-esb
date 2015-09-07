<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:a="http://esb.resanet.com/A"
	xmlns:esb="http://esb.resanet.com/final" exclude-result-prefixes="xsl a">
	<xsl:template match="/">

		<esb:formatFinal>
			<esb:client>
				<xsl:value-of select="/a:formatA/a:unebalise/a:uneAutreBalise/a:id/text()" />
			</esb:client>
		</esb:formatFinal>

	</xsl:template>
</xsl:stylesheet>