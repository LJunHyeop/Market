import React from 'react';

import './css/Header.css' ;

const Header = () => {
    return (
      <header>
        <div className="header-left">
          <img src="logo.png" alt="당근 로고" className="logo" /> {/* 로고 이미지 */}
          <nav>
            <ul>
              <li><a href="#">중고거래</a></li>
              <li><a href="#">동네업체</a></li>
              <li><a href="#">알바</a></li>
              <li><a href="#">부동산</a></li>
              <li><a href="#">중고차 직거래</a></li>
            </ul>
          </nav>
        </div>
        <div className="header-right">
          <input type="text" placeholder="물품이나 동네를 검색해보세요" className="search-box" />
          <button className="chat-btn">채팅하기</button>
        </div>
      </header>
    );
  };

export default Header ;