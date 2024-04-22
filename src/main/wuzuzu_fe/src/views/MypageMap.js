import "../assets/css/map.css";
import React, { useEffect, useState, useRef } from "react";
import Swal from "sweetalert2";

const { kakao } = window;

const getCurrentCoordinate = async () => {
  console.log("getCurrentCoordinate 함수 실행!!!");
  console.log("navigator.geolocation", navigator.geolocation);
  return new Promise((res, rej) => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(function (position) {
        console.log(position);
        const lat = position.coords.latitude; // 위도
        const lon = position.coords.longitude; // 경도
        const coordinate = new kakao.maps.LatLng(lat, lon);
        res(coordinate);
      });
    } else {
      rej(new Error("현재 위치를 불러올 수 없습니다."));
    }
  });
};

const MypageMap = () => {
  const [keyword, setKeyword] = useState("동물 병원");
  const [places, setPlaces] = useState([]);
  const mapRef = useRef(null);
  const placesListRef = useRef(null);
  const menuWrapRef = useRef(null);
  const paginationRef = useRef(null);
  let infowindow;
  let map;
  let markers = [];

  const handleKeywordChange = (e) => {
    setKeyword(e.target.value);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    searchPlaces();
  };

  const searchPlaces = async () => {
    try {
      const currentCoordinate = await getCurrentCoordinate();
      const ps = new kakao.maps.services.Places();
      const options = {
        location: currentCoordinate,
        radius: 10000,
        sort: kakao.maps.services.SortBy.DISTANCE,
      };

      const placesSearchCB = (data, status, pagination) => {
        if (status === kakao.maps.services.Status.OK) {
          setPlaces(data);
          displayPlaces(data);
          displayPagination(pagination);
        } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
          Swal.fire("검색 결과가 존재하지 않습니다.");
          return;
        } else if (status === kakao.maps.services.Status.ERROR) {
          Swal.fire("검색 결과 중 오류가 발생했습니다.");
          return;
        }
      };

      const displayPlaces = (places) => {
        const listEl = placesListRef.current;
        const menuEl = menuWrapRef.current;
        const bounds = new kakao.maps.LatLngBounds(); // 새로운 bounds 생성

        listEl.innerHTML = "";

        // 기존 마커 제거
        markers.forEach((marker) => {
          marker.setMap(null);
        });
        markers = [];

        for (var i = 0; i < places.length; i++) {
          var placePosition = new kakao.maps.LatLng(places[i].y, places[i].x),
              marker = addMarker(placePosition, i),
              itemEl = getListItem(i, places[i]);

          bounds.extend(placePosition); // 검색된 장소들의 위치를 기반으로 bounds 확장

          (function (marker, title) {
            itemEl.addEventListener("mouseover", function () {
              displayInfowindow(marker, title);
            });

            itemEl.addEventListener("mouseout", function () {
              removeInfowindow();
            });
          })(marker, places[i].place_name);

          listEl.appendChild(itemEl);
        }

        menuEl.scrollTop = 0;

        if (map) {
          map.setBounds(bounds); // 지도의 중심을 새로운 bounds로 이동
        }
      };

      const getListItem = (index, places) => {
        var el = document.createElement("li"),
            itemStr =
                '<span class="markerbg marker_' +
                (index + 1) +
                '"></span>' +
                '<div class="info">' +
                "   <h5>" +
                places.place_name +
                "</h5>";

        if (places.road_address_name) {
          itemStr +=
              "    <span>" +
              places.road_address_name +
              "</span>" +
              '   <span class="jibun gray">' +
              places.address_name +
              "</span>";
        } else {
          itemStr += "    <span>" + places.address_name + "</span>";
        }

        itemStr +=
            '  <span class="tel">' + places.phone + "</span>" + "</div>";

        el.innerHTML = itemStr;
        el.className = "item";

        return el;
      };

      function addMarker(position, idx) {
        var imageSrc =
            "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png";
        var imageSize = new kakao.maps.Size(36, 37);
        var imgOptions = {
          spriteSize: new kakao.maps.Size(36, 691),
          spriteOrigin: new kakao.maps.Point(0, idx * 46 + 10),
          offset: new kakao.maps.Point(13, 37),
        };
        var markerImage = new kakao.maps.MarkerImage(
            imageSrc,
            imageSize,
            imgOptions
        );
        var marker = new kakao.maps.Marker({
          position: position,
          image: markerImage,
        });
        marker.setMap(map);
        markers.push(marker);
        return marker;
      }

      function displayInfowindow(marker, title) {
        if (infowindow) {
          var content =
              '<div style="padding:10px;z-index:1;color:black;font-weight:bold">' +
              title +
              "</div>";
          infowindow.setContent(content);
          infowindow.open(map, marker);
        }
      }

      function removeInfowindow() {
        if (infowindow) {
          infowindow.close();
        }
      }

      function displayPagination(pagination) {
        const paginationEl = paginationRef.current;
        const fragment = document.createDocumentFragment();
        for (var i = 1; i <= pagination.last; i++) {
          var el = document.createElement("a");
          el.href = "#";
          el.innerHTML = i;
          if (i === pagination.current) {
            el.className = "on";
          } else {
            el.onclick = (function (i) {
              return function () {
                pagination.gotoPage(i);
              };
            })(i);
          }
          fragment.appendChild(el);
        }
        paginationEl.innerHTML = "";
        paginationEl.appendChild(fragment);
      }

      ps.keywordSearch(keyword, placesSearchCB, options);
    } catch (error) {
      console.error("검색 중 오류 발생:", error);
    }
  };

  useEffect(() => {
    const mapContainer = mapRef.current;
    const listEl = placesListRef.current;
    const menuEl = menuWrapRef.current;
    const paginationEl = paginationRef.current;

    try {
      if (!mapContainer || !listEl || !menuEl || !paginationEl) return;

      const mapOption = {
        center: new kakao.maps.LatLng(33.450701, 126.570667),
        level: 3,
      };
      map = new kakao.maps.Map(mapContainer, mapOption);
      const mapTypeControl = new kakao.maps.MapTypeControl();
      map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);
      const zoomControl = new kakao.maps.ZoomControl();
      map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);
      infowindow = new kakao.maps.InfoWindow({ zIndex: 1 });

      // searchPlaces(); // 이 부분은 검색을 자동으로 실행하고 싶다면 주석 해제
    } catch (err) {
      console.log(err);
    }
  }, []);

  return (
      <div className="map_wrap">
        <div
            ref={mapRef}
            id="map"
            style={{
              width: "800px",
              height: "500px",
              position: "relative",
              overflow: "hidden",
              margin: "10px",
            }}
        ></div>
        <div id="menu_wrap" className="bg_white" ref={menuWrapRef}>
          <div>
            <form onSubmit={handleSubmit}>
              키워드 :{" "}
              <input
                  type="text"
                  value={keyword}
                  onChange={handleKeywordChange}
                  size="15"
              />
              <button type="submit">검색하기</button>
            </form>
          </div>
          <hr />
          <ul id="placesList" ref={placesListRef}></ul>
          <div id="pagination" ref={paginationRef}></div>
        </div>
      </div>
  );
};

export default MypageMap;
