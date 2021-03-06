package teststate

import japgolly.scalajs.react._
import japgolly.scalajs.react.test._
import teststate.domzipper.DomZipper.CssSelEngine
import ExtScalaJsReact._

trait ExtScalaJsReact extends domzipper.Exports {

  implicit def toReactExtHtmlScrubObject(a: HtmlScrub.type): ReactExtHtmlScrubObject =
    new ReactExtHtmlScrubObject(a)

  implicit def toExtScalaJsReactCompExt(m: GenericComponent.MountedRaw): ExtScalaJsReactCompExt =
    new ExtScalaJsReactCompExt(m)

  implicit override val htmlScrub: HtmlScrub =
    HtmlScrub.default >> HtmlScrub.removeReactInternals
}

// ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

object ExtScalaJsReact extends ExtScalaJsReact {

  final class ReactExtHtmlScrubObject(private val self: HtmlScrub.type) extends AnyVal {
    @deprecated("Use .removeReactInternals", "2.1.2") def removeReactDataAttr: HtmlScrub = removeReactInternals

    def removeReactInternals: HtmlScrub =
      HtmlScrub(ReactTestUtils.removeReactInternals)
  }

  final class ExtScalaJsReactCompExt(private val m: GenericComponent.MountedRaw) extends AnyVal {
    def domZipper(implicit $: CssSelEngine, scrub: HtmlScrub): DomZipperAt[vdom.TopNode] =
      DomZipper(m.displayName, ReactDOM.raw findDOMNode m.raw)($, scrub)

    def htmlDomZipper(implicit $: CssSelEngine, scrub: HtmlScrub): HtmlDomZipper =
      domZipper($, scrub).asHtml
  }
}

