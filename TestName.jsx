import React, { useEffect, useState } from "react";

const TestName = () => {
  const [name, setName] = useState("Loading...");

  useEffect(() => {
    const fetchName = async () => {
      try {
        const response = await fetch("http://localhost:8080/api/test");

        if (!response.ok) throw new Error("Failed to fetch");

        const data = await response.json();

        if (data.length > 0) {
          setName(data[0].name);
        } else {
          setName("No data found");
        }

      } catch (error) {
        console.error(error);
        setName("Error fetching data");
      }
    };

    fetchName();
  }, []);

  return (
    <div>
      <h1>Name From Backend:</h1>
      <p>{name}</p>
    </div>
  );
};

export default TestName;

