const PrevArrow = (props) => {
  const { className, onClick, icon } = props;
  return (
    <button className={`slick-arrow slick-prev ${className}`} onClick={onClick}>
      {icon ? <i className={icon}></i> : <i className="icon-chevron-left"></i>}
    </button>
  );
};

export default PrevArrow;
