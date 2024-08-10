import React, { useEffect, useState } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import { Container, Paper, Button } from '@material-ui/core';

const useStyles = makeStyles((theme) => ({
  root: {
    '& > *': {
      margin: theme.spacing(1),
    },
  },
}));

export default function BeautyProduct() {
    const paperStyle = { padding: '50px 20px', width: 600, margin: '20px auto' };
    const [brandName, setBrandName] = useState('');
    const [productName, setProductName] = useState('');
    const [amount, setAmount] = useState('');
    const [description, setDescription] = useState('');
    const [image, setImage] = useState(null);
    const [products, setProducts] = useState([]);
    const classes = useStyles();

    const handleClick = (e) => {
        e.preventDefault();
        const formData = new FormData();
        formData.append('brandName', brandName);
        formData.append('productName', productName);
        formData.append('amount', amount);
        formData.append('description', description);
        if (image) formData.append('file', image);

        fetch("http://localhost:8080/products/add", {
            method: "POST",
            body: formData,
        }).then(() => {
            console.log("New product added");
            fetchProducts(); // Refresh product list after adding a new product
        });
    };

    const fetchProducts = () => {
        fetch("http://localhost:8080/products/getAll")
            .then(res => res.json())
            .then((result) => {
                setProducts(result);
            });
    };

    useEffect(() => {
        fetchProducts();
    }, []);

    return (
        <Container>
            <Paper elevation={3} style={paperStyle}>
                <h1 style={{ color: "blue" }}><u>Add Beauty Product</u></h1>

                <form className={classes.root} noValidate autoComplete="off">
                    <TextField
                        id="outlined-basic"
                        label="Brand Name"
                        variant="outlined"
                        fullWidth
                        value={brandName}
                        onChange={(e) => setBrandName(e.target.value)}
                    />
                    <TextField
                        id="outlined-basic"
                        label="Product Name"
                        variant="outlined"
                        fullWidth
                        value={productName}
                        onChange={(e) => setProductName(e.target.value)}
                    />
                    <TextField
                        id="outlined-basic"
                        label="Amount"
                        variant="outlined"
                        fullWidth
                        type="number"
                        value={amount}
                        onChange={(e) => setAmount(e.target.value)}
                    />
                    <TextField
                        id="outlined-basic"
                        label="Description"
                        variant="outlined"
                        fullWidth
                        value={description}
                        onChange={(e) => setDescription(e.target.value)}
                    />
                    <input
                        type="file"
                        accept="image/*"
                        onChange={(e) => setImage(e.target.files[0])}
                    />
                    <Button
                        variant="contained"
                        color="secondary"
                        onClick={handleClick}
                    >
                        Submit
                    </Button>
                </form>
            </Paper>

            <h1>Beauty Products</h1>

            <Paper elevation={3} style={paperStyle}>
                {products.map(product => (
                    <Paper
                        elevation={6}
                        style={{ margin: "10px", padding: "15px", textAlign: "left" }}
                        key={product.id}
                    >
                        Id: {product.id}<br />
                        Brand Name: {product.brandName}<br />
                        Product Name: {product.productName}<br />
                        Amount: ${product.amount}<br />
                        Description: {product.description}<br />
                        {product.imageUrl && (
                            <img
                                src={product.imageUrl}
                                alt={product.productName}
                                style={{ width: '100px', height: '100px', objectFit: 'cover' }}
                            />
                        )}
                    </Paper>
                ))}
            </Paper>
        </Container>
    );
}
