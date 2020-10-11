import Head from "next/head";
import { useState, useEffect } from "react";
import { wrapper } from "../management/store";
import styles from "../styles/Home.module.css";
import Layout from "../components/layouts/Layout";
import HomeBody from "../components/layouts/main/HomeBody";
import SubscribePopup from "../components/shared/SubscribePopup";
import { appSettings } from "../utils/app-settings";

const Home = () => {
  const [subscribe, setSubscribe] = useState(false);

  useEffect(() => {
    setTimeout(() => {
      setSubscribe(true);
    }, appSettings.subscribePopDelay);
  }, []);

  return (
    <Layout>
      <div className={styles.container}>
        <Head>
          <title>Create Next App</title>
        </Head>
        <SubscribePopup active={subscribe} />
        <main className={styles.main}>
          <HomeBody />
        </main>

        <footer className={styles.footer}></footer>
      </div>
    </Layout>
  );
};

export const getStaticProps = wrapper.getStaticProps(
  async ({ store, preview }) => {
    console.log("2. Page.getStaticProps uses the store to dispatch things");
    /*console.log(store.getState());
    return {
      props: { store: store.getState() },
    };
    //store.dispatch({type: 'TICK', payload: 'was set in other page ' + preview});*/
  }
);

export default Home;
