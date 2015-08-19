import java.awt.Font
import java.awt.font.FontRenderContext

object SvgGenerator {
  /**
   * Generate svg based on several parameters.
   */
  def generate(subject: String, status: String, color: Option[String] = Option.empty): String = {
    val subjectWidth = calcStringWidth(subject)
    val statusWidth = calcStringWidth(status)
    val subjectColor = "#555"
    val statusColor = "#" + color.getOrElse("4c1")
    val logo = ""
    val logoWidth = 0
    val logoPadding = 0

    /**
     * Scala string interpolation is used to turn a template into proper svg.
     *
     * More about string interpolation: http://docs.scala-lang.org/overviews/core/string-interpolation.html
     */
    s"""<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="${subjectWidth+statusWidth}" height="20">
      |  <linearGradient id="smooth" x2="0" y2="100%">
      |    <stop offset="0" stop-color="#bbb" stop-opacity=".1"/>
      |    <stop offset="1" stop-opacity=".1"/>
      |  </linearGradient>
      |
      |  <mask id="round">
      |    <rect width="${subjectWidth+statusWidth}" height="20" rx="3" fill="#fff"/>
      |  </mask>
      |
      |  <g mask="url(#round)">
      |    <rect width="$subjectWidth" height="20" fill="$subjectColor"/>
      |    <rect x="$subjectWidth" width="$statusWidth" height="20" fill="$statusColor"/>
      |    <rect width="${subjectWidth+statusWidth}" height="20" fill="url(#smooth)"/>
      |  </g>
      |
      |  <g fill="#fff" text-anchor="middle" font-family="DejaVu Sans,Verdana,Geneva,sans-serif" font-size="11">
      |    <!--{{?it.logo}}
      |      <image x="5" y="3" width="$logoWidth" height="14" xlink:href="$logo"/>
      |    {{?}}-->
      |    <text x="${(subjectWidth+logoWidth+logoPadding)/2}" y="15" fill="#010101" fill-opacity=".3">$subject</text>
      |    <text x="${(subjectWidth+logoWidth+logoPadding)/2}" y="14">$subject</text>
      |    <text x="${subjectWidth+statusWidth/2-1}" y="15" fill="#010101" fill-opacity=".3">$status</text>
      |    <text x="${subjectWidth+statusWidth/2-1}" y="14">$status</text>
      |  </g>
      |</svg>""".stripMargin
  }

  def calcStringWidth(str: String): Int = {
    val text: String = "Hello World"
    val font: Font = new Font("DejaVu Sans,Verdana,Geneva,sans-serif", Font.PLAIN, 11)
    val frc: FontRenderContext = new FontRenderContext(font.getTransform, true, true)
    val textwidth: Int = font.getStringBounds(text, frc).getWidth.toInt
    val textheight: Int = font.getStringBounds(text, frc).getHeight.toInt
    textwidth
  }
}
