import React from 'react';

import './todo-list-item.css';
import ItemEditForm from "../item-edit-form";

const TodoListItem = ({ label, onToggleDone, onDelete, onEdit, toEdit }) => {

  let content = label;
  if (toEdit) {
      content = <ItemEditForm />;
  }

  return (
    <span className="todo-list-item">
      <span
        className="todo-list-item-label"
        onClick={onToggleDone}>{content}</span>

      <button type="button"
              className="btn btn-outline-danger btn-sm float-right"
              onClick={onDelete}>
        <i className="fa fa-trash-o"> </i>
      </button>

      <button type="button"
              className="btn btn-outline-success btn-sm float-right"
              onClick={onEdit}>
        <i className="fa fa-edit"> </i>
      </button>
    </span>
  );
};

export default TodoListItem;