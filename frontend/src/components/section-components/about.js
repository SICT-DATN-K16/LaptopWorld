import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import parse from 'html-react-parser';

class AboutSection extends Component {

    render() {

        let publicUrl = process.env.PUBLIC_URL+'/'

    return    <section className="about-area text-center pd-top-100 pd-bottom-70" style={{background: 'url('+publicUrl+'assets/img/about/bg.png)'}}>
				  <div className="container">
				    <div className="row justify-content-center">
				      <div className="col-lg-3 col-sm-6">
				        <div className="single-about-wrap">
				          <div className="thumb">
				            <img src={publicUrl+"assets/img/about/1.png"} alt="img" />
				          </div>
				          <h5><a href="#">Miễn phí vận chuyển</a></h5>
				          <p>Hỏa tốc trong 2h</p>
				        </div>
				      </div>
				      <div className="col-lg-3 col-sm-6">
				        <div className="single-about-wrap">
				          <div className="thumb">
				            <img src={publicUrl+"assets/img/about/2.png"} alt="img" />
				          </div>
				          <h5><a href="#">Thanh toán bảo mật</a></h5>
				          <p>Giao dịch an toàn</p>
				        </div>
				      </div>
				      <div className="col-lg-3 col-sm-6">
				        <div className="single-about-wrap">
				          <div className="thumb">
				            <img src={publicUrl+"assets/img/about/3.png"} alt="img" />
				          </div>
				          <h5><a href="#">Đổi trả miễn phí</a></h5>
				          <p>30 ngày đổi trả</p>
				        </div>
				      </div>
				      <div className="col-lg-3 col-sm-6">
				        <div className="single-about-wrap">
				          <div className="thumb">
				            <img src={publicUrl+"assets/img/about/4.png" }alt="img" />
				          </div>
				          <h5><a href="#">Hỗ trợ 24/7</a></h5>
				          <p>Hỗ trợ nhiệt tình</p>
				        </div>
				      </div>
				    </div>
				  </div>      
				</section>


        }
}

export default AboutSection