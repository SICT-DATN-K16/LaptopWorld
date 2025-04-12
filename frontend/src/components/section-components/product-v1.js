import React, { Component } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';

class ProductV1 extends Component {
  constructor(props) {
    super(props);
    this.state = {
      products: []
    };
  }

  componentDidMount() {
    axios.get('http://localhost:8080/api/products/top-rate?page=0&size=10')
      .then(response => {
        this.setState({ products: response.data });
      })
      .catch(error => {
        console.error('Lỗi khi load sản phẩm:', error);
      });
  }

  render() {
    let publicUrl = process.env.PUBLIC_URL + '/';

    return (
      <section className="all-item-area pd-top-90 pd-bottom-100">
        <div className="container">
          <div className="row">
            <div className="col-lg-4">
              <div className="section-title">
                <h2>All Items</h2>
              </div>
            </div>
            <div className="col-lg-8 mt-2">
              <div className="isotope-filters item-isotope-btn text-lg-right">
                <button className="button active ml-0" data-filter="*">All Items</button>
                <button className="button" data-filter=".cat-1">Site Templates</button>
                <button className="button" data-filter=".cat-2">UI Templates</button>
                <button className="button" data-filter=".cat-3">All Items</button>
              </div>
            </div>
          </div>

          <div className="all-item-section go-top">
            <div className="item-isotope row">
              <div className="item-sizer" />
              {/* Hiển thị sản phẩm từ API */}
              {this.state.products.map(product => (
                <div className="all-isotope-item col-xl-3 col-lg-4 col-md-5 col-sm-6 cat-1 cat-3" key={product.id}>
                  <div className="thumb">
                    <a className="gallery-fancybox" href="#">
                      <img src={`http://localhost:8080/images/product/${product.productImages[0].image}`} alt={product.name} />
                    </a>
                    <a className="btn btn-white" href="#">Live Preview</a>
                  </div>
                  <div className="item-details">
                    <span className="price">{new Intl.NumberFormat('vi-VN').format(product.price)} đ</span>
                    <h4><Link to={`/product-details/${product.id}`}>{product.name}</Link></h4>
                    <p>{product.shortDesc || "No description"}</p>
                  </div>
                </div>
              ))}
            </div>
          </div>

          <div className="row">
            <div className="col-lg-12 text-center">
              <Link className="btn btn-base" to="/product">More Products</Link>
            </div>
          </div>
        </div>
      </section>
    );
  }
}

export default ProductV1;
