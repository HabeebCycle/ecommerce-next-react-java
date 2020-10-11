import DocumentTop from "./DocumentTop";
import TopBar from "./TopBar";
import HeaderMain from "./HeaderMain";
import HeaderMobile from "../../shared/mobile/HeaderMobile";
import NavigationList from "../../shared/mobile/NavigationList";

export default function Header() {
  return (
    <>
      <DocumentTop />
      <TopBar />
      <HeaderMain />
      <HeaderMobile />
      <NavigationList />
    </>
  );
}
