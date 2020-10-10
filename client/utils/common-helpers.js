import { LOCAL_STORAGE_NAME } from "./constants";

export const stickyHeader = () => {
  let number =
    window.pageXOffset ||
    document.documentElement.scrollTop ||
    document.body.scrollTop ||
    0;
  const header = document.getElementById("headerSticky");
  if (header !== null) {
    if (number >= 300) {
      header.classList.add("header--sticky");
    } else {
      header.classList.remove("header--sticky");
    }
  }
};

export const getLocalStorageData = () => {
  if (process.browser) {
    return JSON.parse(localStorage.getItem(LOCAL_STORAGE_NAME));
  }
};

export const setLocalStorageData = (obj) => {
  if (process.browser) {
    localStorage.setItem(LOCAL_STORAGE_NAME, JSON.stringify(obj));
  }
};
